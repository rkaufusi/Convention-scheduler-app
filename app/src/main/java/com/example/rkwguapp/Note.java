package com.example.rkwguapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.Adapters.NoteAdapter;
import com.example.rkwguapp.Adapters.SubjectAdapter;
import com.example.rkwguapp.Entities.NotesEntity;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.ViewModels.NoteViewModel;
import com.example.rkwguapp.ViewModels.SubjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel noteViewModel;
    private EditText editNoteTitle;

    public static int numberOfNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_course);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Note.this, NoteDetail.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter NoteAdapter = new NoteAdapter();
        recyclerView.setAdapter(NoteAdapter);

        Intent intent = getIntent();

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<NotesEntity>>() {
            @Override
            public void onChanged(@Nullable List<NotesEntity> notesEntities) {
                NoteAdapter.submitList(notesEntities);
            }
        });

        // edit note
        NoteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotesEntity NotesEntity) {
                Intent intent = new Intent(Note.this, NoteDetail.class);
                intent.putExtra(NoteDetail.EXTRA_ID_C, NotesEntity.getId());
                intent.putExtra(NoteDetail.EXTRA_TITLE_NOTE, NotesEntity.getNoteTitle());
                intent.putExtra(NoteDetail.EXTRA_BODY, NotesEntity.getNoteBody());
                intent.putExtra(NoteDetail.EXTRA_NOTE_WORKSHOP, NotesEntity.getNoteWorkshop());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.workshop_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(NoteDetail.EXTRA_TITLE_NOTE);
            String body = data.getStringExtra(NoteDetail.EXTRA_BODY);
            String noteWorkshop = data.getStringExtra(NoteDetail.EXTRA_NOTE_WORKSHOP);

            NotesEntity note = new NotesEntity(title, body, noteWorkshop);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(NoteDetail.EXTRA_ID_C, -1);
            String title = data.getStringExtra(NoteDetail.EXTRA_TITLE_NOTE);
            String body = data.getStringExtra(NoteDetail.EXTRA_BODY);
            String noteWorkshop = data.getStringExtra(NoteDetail.EXTRA_NOTE_WORKSHOP);

            NotesEntity note = new NotesEntity(title, body, noteWorkshop);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}