package com.example.rkwguapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.Observer;

import com.example.rkwguapp.Adapters.ConventionDetailAdapter;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.Entities.ConventionEntity;
import com.example.rkwguapp.ViewModels.SubjectViewModel;
import com.example.rkwguapp.ViewModels.ConventionViewModel;

public class ConventionDetail extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.rkwguapp.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.rkwguapp.EXTRA_TITLE";
    public static final String EXTRA_DATE = "com.example.rkwguapp.EXTRA_DATE";
    public static final String EXTRA_DATE2 = "com.example.rkwguapp.EXTRA_DATE2";



    private List<SubjectEntity> subjectData = new ArrayList<>();

    final Calendar c = Calendar.getInstance();
    final Calendar c2 = Calendar.getInstance();
    TextView textView;
    TextView textView2;
    //TextView associatedSubjects;
    RecyclerView associatedSubjects;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;

    private EditText editConventionTitle;
    // test
    public SubjectViewModel subjectViewModel;
    public ConventionViewModel conventionViewModel;
    public static int numberOfSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convention_detail);

        editConventionTitle = findViewById(R.id.edit_convention_title);
        textView = findViewById(R.id.start_date_textview);
        textView2 = findViewById(R.id.end_date_textview);
        associatedSubjects = findViewById(R.id.associated_subjects);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__close_2);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TITLE)) {
            setTitle("Convention Details");
            editConventionTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            textView.setText(intent.getStringExtra(EXTRA_DATE));
            textView2.setText(intent.getStringExtra(EXTRA_DATE2));

        } else {
            setTitle("Add Convention");
        }

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                changeStart();
            }

        };

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ConventionDetail.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c2.set(Calendar.YEAR, year);
                c2.set(Calendar.MONTH, monthOfYear);
                c2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                changeEnd();
            }

        };
        textView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ConventionDetail.this, date2, c2
                        .get(Calendar.YEAR), c2.get(Calendar.MONTH),
                        c2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //---------populate recyclerview with Convention Subjects---------

        final RecyclerView recyclerView = findViewById(R.id.associated_subjects);
        //final SubjectAdapter adapter = new SubjectAdapter();
        final ConventionDetailAdapter adapter = new ConventionDetailAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //subjectViewModel = new ViewModelProvider(this).get(subjectViewModel.class);
        //subjectViewModel.getAllSubjects().observe(this, new Observer<List<SubjectEntity>>() {

        subjectViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(SubjectViewModel.class);
        subjectViewModel.getAllSubjects().observe(this, new Observer<List<SubjectEntity>>() {
            @Override
            public void onChanged(@Nullable final List<SubjectEntity> subjects) {
                List<SubjectEntity> filteredSubjects = new ArrayList<>();

                for (SubjectEntity p : subjects) {
                    String currentConvention = editConventionTitle.getText().toString();
                    String associatedConvention = p.getAssociatedConvention();

                    if (associatedConvention.equals(currentConvention)) {
                        filteredSubjects.add(p);
                        adapter.setCourses(filteredSubjects);
                    }
                    numberOfSubjects = filteredSubjects.size();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_convention_menu, menu);
        return true;
    }

    private void deleteConvention() {
            if(numberOfSubjects == 0) {
                conventionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ConventionViewModel.class);
                conventionViewModel.getAllConventions().observe(this, new Observer<List<ConventionEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<ConventionEntity> words) {
                        // Update the cached copy of the words in the adapter.
                        List<SubjectEntity> filteredWords = new ArrayList<>();
                        for (ConventionEntity p : words){
                            if (p.getConventionTitle().equals(editConventionTitle.getText().toString())) {

                                conventionViewModel.delete(p);
                                Toast.makeText(getApplicationContext(), "Convention Deleted", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(ConventionDetail.this, Convention.class);
                                    startActivity(i);
                            }
                        }
                    }
                });
            } else{
                Toast.makeText(getApplicationContext(),"Can't delete. Contains one or more Subjects", Toast.LENGTH_LONG).show();
            }
    }

    private void saveConvention() {
        String title = editConventionTitle.getText().toString();
        String date = textView.getText().toString();
        String date2 = textView2.getText().toString();

        if (title.trim().isEmpty() || date.trim().isEmpty() || date2.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title, start and end date", Toast.LENGTH_SHORT).show();
            return;
        }

        //validate start is before end
        Date d = DateConverter.toDateType(date);
        Date d2 = DateConverter.toDateType(date2);

        if (d.compareTo(d2) > 0) {
            Toast.makeText(this, "Start Date must be before End Date", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_DATE2, date2);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_convention:
                saveConvention();
                return true;
            case R.id.delete:
                deleteConvention();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeStart() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(c.getTime()));
    }
    private void changeEnd() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView2.setText(sdf.format(c2.getTime()));
    }

}
