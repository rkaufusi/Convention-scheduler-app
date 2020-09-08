package com.example.rkwguapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rkwguapp.Adapters.SubjectAdapter;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.ViewModels.SubjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Subject extends AppCompatActivity {
    public static final int ADD_SUBJECT_REQUEST = 1;
    public static final int EDIT_SUBJECT_REQUEST = 2;

    private SubjectViewModel subjectViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        FloatingActionButton buttonAddSubject = findViewById(R.id.button_add_course);
        buttonAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subject.this, SubjectDetail.class);
                startActivityForResult(intent, ADD_SUBJECT_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SubjectAdapter SubjectAdapter = new SubjectAdapter();
        recyclerView.setAdapter(SubjectAdapter);

        subjectViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(SubjectViewModel.class);
        subjectViewModel.getAllSubjects().observe(this, new Observer<List<SubjectEntity>>() {
            @Override
            public void onChanged(@Nullable List<SubjectEntity> subjectEntities) {
                SubjectAdapter.submitList(subjectEntities);
            }
        });

        // edit subject
        SubjectAdapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SubjectEntity SubjectEntity) {
                Intent intent = new Intent(Subject.this, SubjectDetail.class);
                intent.putExtra(SubjectDetail.EXTRA_ID_C, SubjectEntity.getSubjectId());
                intent.putExtra(SubjectDetail.EXTRA_TITLE_SUBJECT, SubjectEntity.getSubjectTitle());
                // String formatter
                String pattern = "MM-dd-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);

                String date = sdf.format(SubjectEntity.getSubjectStartDate());
                intent.putExtra(SubjectDetail.EXTRA_DATE_SUBJECT, date);
                String date2 = sdf.format((SubjectEntity.getSubjectEndDate()));
                intent.putExtra(SubjectDetail.EXTRA_DATE2_SUBJECT, date2);

                intent.putExtra(SubjectDetail.EXTRA_ASSOCIATED_CONVENTION, SubjectEntity.getAssociatedConvention());

                startActivityForResult(intent, EDIT_SUBJECT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_SUBJECT_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(SubjectDetail.EXTRA_TITLE_SUBJECT);
            String associatedConvention = data.getStringExtra(SubjectDetail.EXTRA_ASSOCIATED_CONVENTION);
            String date = data.getStringExtra(SubjectDetail.EXTRA_DATE_SUBJECT);
            String date2 = data.getStringExtra(SubjectDetail.EXTRA_DATE2_SUBJECT);

            Date start = DateConverter.toDateType(date);
            Date end = DateConverter.toDateType(date2);

            SubjectEntity subject = new SubjectEntity(title, associatedConvention, start, end);
            subjectViewModel.insert(subject);

            Toast.makeText(this, "Subject Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_SUBJECT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(SubjectDetail.EXTRA_ID_C, -1);
            String title = data.getStringExtra(SubjectDetail.EXTRA_TITLE_SUBJECT);
            String associatedConvention = data.getStringExtra(SubjectDetail.EXTRA_ASSOCIATED_CONVENTION);
            String date = data.getStringExtra(SubjectDetail.EXTRA_DATE_SUBJECT);
            String date2 = data.getStringExtra(SubjectDetail.EXTRA_DATE2_SUBJECT);

            Date start = DateConverter.toDateType(date);
            Date end = DateConverter.toDateType(date2);

            SubjectEntity subject = new SubjectEntity(title, associatedConvention, start, end);
            subject.setSubjectId(id);
            subjectViewModel.update(subject);

            Toast.makeText(this, "Subject Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Subject Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}