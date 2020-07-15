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

public class MainCourse extends AppCompatActivity {
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;

    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_course);

        FloatingActionButton buttonAddCourse = findViewById(R.id.button_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCourse.this, AddEditCourses.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        final CourseAdapter courseAdapter = new CourseAdapter();
        recyclerView.setAdapter(courseAdapter);

        courseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable List<CourseEntity> courseEntities) {
                courseAdapter.submitList(courseEntities);

            }
        });
// Delete course by swiping. Possible add "delete All Courses" later
        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                courseViewModel.delete(courseAdapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainCourse.this, "Course Deleted", Toast.LENGTH_SHORT).show();
            }
        })).attachToRecyclerView(recyclerView);

        // edit course
        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CourseEntity courseEntity) {
                Intent intent = new Intent(MainCourse.this, AddEditCourses.class);
                intent.putExtra(AddEditCourses.EXTRA_TITLE_COURSE, courseEntity.getCourseTitle());
                // String formatter
                String pattern = "MM-dd-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);

                String date = sdf.format(courseEntity.getCourseStartDate());
                intent.putExtra(AddEditCourses.EXTRA_DATE_COURSE, date);
                String date2 = sdf.format((courseEntity.getCourseEndDate()));
                intent.putExtra(AddEditCourses.EXTRA_DATE2_COURSE, date2);

                intent.putExtra(AddEditCourses.EXTRA_TERM, courseEntity.getAssociatedTerm());
                intent.putExtra(AddEditCourses.EXTRA_STATUS, courseEntity.getCourseStatus());
                intent.putExtra(AddEditCourses.EXTRA_MENTOR, courseEntity.getCourseMentor());
                intent.putExtra(AddEditCourses.EXTRA_PHONE, courseEntity.getCourseMentorPhone());
                intent.putExtra(AddEditCourses.EXTRA_EMAIL, courseEntity.getCourseMentorEmail());
                intent.putExtra(AddEditCourses.EXTRA_NOTE, courseEntity.getCourseNote());

                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddEditCourses.EXTRA_TITLE_COURSE);
            String associatedTerm = data.getStringExtra(AddEditCourses.EXTRA_TERM);
            String date = data.getStringExtra(AddEditCourses.EXTRA_DATE_COURSE);
            String date2 = data.getStringExtra(AddEditCourses.EXTRA_DATE2_COURSE);
            String courseStatus = data.getStringExtra(AddEditCourses.EXTRA_STATUS);
            String mentorName = data.getStringExtra(AddEditCourses.EXTRA_MENTOR);
            String mentorPhone = data.getStringExtra(AddEditCourses.EXTRA_PHONE);
            String mentorEmail = data.getStringExtra(AddEditCourses.EXTRA_EMAIL);
            String courseNote = data.getStringExtra(AddEditCourses.EXTRA_NOTE);

            Date start = DateConverter.toDateType(date);
            Date end = DateConverter.toDateType(date2);

            CourseEntity course = new CourseEntity(title, associatedTerm, start, end, courseStatus, mentorName, mentorPhone, mentorEmail, courseNote);
            courseViewModel.insert(course);

            Toast.makeText(this, "Course Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddEditCourses.EXTRA_TITLE_COURSE);
            String associatedTerm = data.getStringExtra(AddEditCourses.EXTRA_TERM);
            String date = data.getStringExtra(AddEditCourses.EXTRA_DATE_COURSE);
            String date2 = data.getStringExtra(AddEditCourses.EXTRA_DATE2_COURSE);
            String courseStatus = data.getStringExtra(AddEditCourses.EXTRA_STATUS);
            String mentorName = data.getStringExtra(AddEditCourses.EXTRA_MENTOR);
            String mentorPhone = data.getStringExtra(AddEditCourses.EXTRA_PHONE);
            String mentorEmail = data.getStringExtra(AddEditCourses.EXTRA_EMAIL);
            String courseNote = data.getStringExtra(AddEditCourses.EXTRA_NOTE);

            Date start = DateConverter.toDateType(date);
            Date end = DateConverter.toDateType(date2);

            CourseEntity course = new CourseEntity(title, associatedTerm, start, end, courseStatus, mentorName, mentorPhone, mentorEmail, courseNote);
            course.setCourseTitle(title);

            courseViewModel.update(course);

            Toast.makeText(this, "Course Updated", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Course Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}