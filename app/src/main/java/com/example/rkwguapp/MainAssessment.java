package com.example.rkwguapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainAssessment extends AppCompatActivity {
    public static final int ADD_ASSESS_REQUEST = 5;
    public static final int EDIT_ASSESS_REQUEST = 6;

    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_assessment);

        FloatingActionButton buttonAddAsses = findViewById(R.id.button_add_assess);
        buttonAddAsses.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent intent = new Intent(MainAssessment.this, AddEditAssessment.class);
                startActivityForResult(intent, ADD_ASSESS_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_assess);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter();
        recyclerView.setAdapter(assessmentAdapter);

       // assessmentViewModel = ViewModelProvider.of(this).get(AssessmentViewModel.class);
        assessmentViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessmentEntities) {
               assessmentAdapter.setAssessmentEntityList(assessmentEntities);
            }
        });

        // swipe to delete
        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.delete(assessmentAdapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainAssessment.this, "Assessment Deleted", Toast.LENGTH_SHORT).show();
            }
        })).attachToRecyclerView(recyclerView);

        // edit assessment
        assessmentAdapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AssessmentEntity assessmentEntity) {
                Intent intent = new Intent(MainAssessment.this, AddEditAssessment.class);
                intent.putExtra(AddEditAssessment.EXTRA_ASSESS_TITLE, assessmentEntity.getAssessmentsTitle());
                intent.putExtra(AddEditAssessment.EXTRA_COURSE, assessmentEntity.getAssociatedCourse());

                String pattern = "MM-dd-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                String date = sdf.format(assessmentEntity.getAssessmentDueDate());
                intent.putExtra(AddEditAssessment.EXTRA_GOAL, date);
                startActivityForResult(intent, EDIT_ASSESS_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ASSESS_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditAssessment.EXTRA_ASSESS_TITLE);
            String course = data.getStringExtra(AddEditAssessment.EXTRA_COURSE);
            String goal = data.getStringExtra(AddEditAssessment.EXTRA_GOAL);

            // convert to date
            Date date = DateConverter.toDateType(goal);

            AssessmentEntity assessmentEntity = new AssessmentEntity(title, course, date);
            assessmentViewModel.insert(assessmentEntity);

            Toast.makeText(this, "Assessment Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_ASSESS_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditAssessment.EXTRA_ASSESS_TITLE);
            String course = data.getStringExtra(AddEditAssessment.EXTRA_COURSE);
            String goal = data.getStringExtra(AddEditAssessment.EXTRA_GOAL);

            Date date = DateConverter.toDateType(goal);

            AssessmentEntity assessmentEntity = new AssessmentEntity(title, course, date);
            assessmentEntity.setAssessmentsTitle(title);

            assessmentViewModel.update(assessmentEntity);

            Toast.makeText(this, "Assessment Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
        }
    }
}