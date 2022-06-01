package com.example.rkwguapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button Conventions
        Button buttonConventions = (Button) findViewById(R.id.button);
        buttonConventions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Convention.class));
            }
        });

        // button Subjects
        Button buttonCourses = (Button) findViewById(R.id.button1);
        buttonCourses.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Subject.class));
            }
        });

        // button Workshops
        Button buttonAssessments = (Button) findViewById(R.id.button2);
        buttonAssessments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Workshop.class));
            }
        });

        // open report
        TextView report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Report.class));
            }
        });

        // open map
        TextView map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapOfConvention.class));
            }
        });
    }
}