package com.example.rkwguapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditAssessment extends AppCompatActivity {

    public static final String EXTRA_COURSE = "com.example.rkwguapp.EXTRA_COURSE";
    public static final String EXTRA_ASSESS_TITLE = "com.example.rkwguapp.EXTRA_ASSESS_TITLE";
    public static final String EXTRA_GOAL = "com.example.rkwguapp.EXTRA_GOAL";

    private EditText editTextCourse;
    private EditText editTextTitle;
    private TextView goalDate;

    DatePickerDialog.OnDateSetListener date;
    final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);

        editTextTitle = findViewById(R.id.edit_text_assess_title);
        editTextCourse = findViewById(R.id.edit_text_assess_course);
        goalDate = findViewById(R.id.text_view_goal);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ASSESS_TITLE)) {
            setTitle("Edit Assessment");
            editTextTitle.setText(intent.getStringExtra(EXTRA_ASSESS_TITLE));
            editTextCourse.setText(intent.getStringExtra(EXTRA_COURSE));
            goalDate.setText(intent.getStringExtra(EXTRA_GOAL));
        } else {
            setTitle("Add Assessment");
        }


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                changeGoal();
            }

        };

        goalDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEditAssessment.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void notificationMethod() throws ParseException {

        String assessDate = goalDate.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss") ;
        Date date1 = sdf.parse(assessDate + " 00:00:00");
        long longDate = date1.getTime();


        Intent intent=new Intent(AddEditAssessment.this, AlertReceiver.class);
        intent.putExtra("key","Take Your Assessment Today");
        PendingIntent sender = PendingIntent.getBroadcast(AddEditAssessment.this,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, longDate, sender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(R.menu.add_term_menu, menu);
        menuInflater.inflate(R.menu.assess_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveAssessment() {

        String title = editTextTitle.getText().toString();
        String course = editTextCourse.getText().toString();
        String goal = goalDate.getText().toString();

        // logic for no empty fields
        if (title.trim().isEmpty() || course.trim().isEmpty() || goal.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title, course and goal date", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ASSESS_TITLE, title);
        data.putExtra(EXTRA_COURSE, course);
        data.putExtra(EXTRA_GOAL, goal);

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
           // case R.id.save_assess:
            case R.id.save_term:
                saveAssessment();
                return true;
            case R.id.assessment_alert:
                try {
                    notificationMethod();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void changeGoal() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        goalDate.setText(sdf.format(c.getTime()));
    }
}