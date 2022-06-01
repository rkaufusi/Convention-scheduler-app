package com.example.rkwguapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.rkwguapp.Entities.NotesEntity;
import com.example.rkwguapp.Entities.SubjectEntity;

import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.ViewModels.NoteViewModel;
import com.example.rkwguapp.ViewModels.WorkshopViewModel;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NoteDetail extends AppCompatActivity {
    public static final String EXTRA_ID_C = "com.example.rkwguapp.EXTRA_ID_C";
    public static final String EXTRA_TITLE_NOTE = "com.example.rkwguapp.EXTRA_TITLE_NOTE";
    public static final String EXTRA_BODY = "com.example.rkwguapp.EXTRA_DATE_SUBJECT";
    public static final String EXTRA_NOTE_WORKSHOP = "com.example.rkwguapp.EXTRA_NOTE_WORKSHOP";

    public static final String CHANNEL_ID = "Start Alert";

    final Calendar c = Calendar.getInstance();
    final Calendar c2 = Calendar.getInstance();

    TextView textViewCourse;
    TextView textView2Course;

    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;

    private EditText editNoteTitle;
    private EditText editNoteBody;
    private Spinner workshopSpinner;

    private TextView mills;
    public WorkshopViewModel workshopViewModel;

    public NoteViewModel noteViewModel;
    public static int numberOfNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail);

        editNoteTitle = findViewById(R.id.edit_note_title);
        editNoteBody = findViewById(R.id.note_body);
        workshopSpinner = findViewById(R.id.workshop_spinner);

        final ArrayList<String> workshopsList = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workshopsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workshopSpinner.setAdapter(adapter);

        final Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TITLE_NOTE)) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);
            setTitle("Edit Note");
            editNoteTitle.setText(intent.getStringExtra(EXTRA_TITLE_NOTE));
            editNoteBody.setText(intent.getStringExtra(EXTRA_BODY));
        } else {
            setTitle("Add Note");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);
        }

        //populate spinner

        workshopViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkshopViewModel.class);
        workshopViewModel.getAllWorkshops().observe(this, new Observer<List<WorkshopEntity>>() {
            @Override
            public void onChanged(@Nullable final List<WorkshopEntity> workshops) {
                for (WorkshopEntity p : workshops) {
                    workshopsList.add(p.getWorkshopTitle());
                    workshopSpinner.setSelection(adapter.getPosition(intent.getStringExtra(EXTRA_NOTE_WORKSHOP)));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note, menu);
        return true;
    }

    private void deleteNote() {
        if(numberOfNotes == 0) {
            noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
            noteViewModel.getAllNotes().observe(this, new Observer<List<NotesEntity>>() {
                @Override
                public void onChanged(@Nullable final List<NotesEntity> words) {
                    List<SubjectEntity> filteredWords = new ArrayList<>();
                    for (NotesEntity p : words){
                        if (p.getNoteTitle().equals(editNoteTitle.getText().toString())) {

                            noteViewModel.delete(p);
                            Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(NoteDetail.this, Subject.class);
                            startActivity(i);
                        }
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Can't delete. Contains one or more Assessments", Toast.LENGTH_LONG).show();
        }

    }

    private void saveNote() {

        String title = editNoteTitle.getText().toString();
        String workshopTitle = workshopSpinner.getSelectedItem().toString();
        String noteBody = editNoteBody.getText().toString();

        // insert logic for no empty values
        if (title.trim().isEmpty() || noteBody.trim().isEmpty()) {
            Toast.makeText(this, "Please make sure every field is complete", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE_NOTE, title);
        data.putExtra(EXTRA_BODY, noteBody);
        data.putExtra(EXTRA_NOTE_WORKSHOP, workshopTitle);

        int id = getIntent().getIntExtra(EXTRA_ID_C, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID_C, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_subject:
                saveNote();
                return true;
            case R.id.delete:
                deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
