package com.example.rkwguapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button 1 Terms
        Button buttonTerms = (Button) findViewById(R.id.button);
        buttonTerms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                startActivity(new Intent(MainActivity.this, MainTerm.class));
            }
        });

        // button Courses
        Button buttonCourses = (Button) findViewById(R.id.button1);
        buttonCourses.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                startActivity(new Intent(MainActivity.this, MainCourse.class));
            }
        });

        // button Assessments
        Button buttonAssessments = (Button) findViewById(R.id.button2);
        buttonAssessments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                startActivity(new Intent(MainActivity.this, MainAssessment.class));
            }
        });

    }
}