package com.example.rkwguapp;

import android.app.AlarmManager;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditCourses extends AppCompatActivity {
    public static final String EXTRA_TITLE_COURSE = "com.example.rkwguapp.EXTRA_TITLE_COURSE";
    public static final String EXTRA_DATE_COURSE = "com.example.rkwguapp.EXTRA_DATE_COURSE";
    public static final String EXTRA_DATE2_COURSE = "com.example.rkwguapp.EXTRA_DATE2_COURSE";
    public static final String EXTRA_TERM = "com.example.rkwguapp.EXTRA_TERM";
    public static final String EXTRA_STATUS = "com.example.rkwguapp.EXTRA_STATUS";
    public static final String EXTRA_MENTOR = "com.example.rkwguapp.EXTRA_MENTOR";
    public static final String EXTRA_PHONE = "com.example.rkwguapp.EXTRA_PHONE";
    public static final String EXTRA_EMAIL = "com.example.rkwguapp.EXTRA_EMAIL";
    public static final String EXTRA_NOTE = "com.example.rkwguapp.EXTRA_NOTE";

    public static final String CHANNEL_ID = "Start Alert";

    final Calendar c = Calendar.getInstance();
    final Calendar c2 = Calendar.getInstance();

    TextView textViewCourse;
    TextView textView2Course;

    Button startNotification;
    Button endNotification;
    Button shareNote;

    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;

    private EditText editCourseTitle;
    private EditText editAssociatedTerm;
    private EditText editStatus;
    private EditText editMentor;
    private EditText editMentorPhone;
    private EditText editMentorEmail;
    private EditText editNote;

    private TextView mills;

    TermEntity termEntity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_courses);

        editCourseTitle = findViewById(R.id.edit_course_title);
        editAssociatedTerm = findViewById(R.id.associated_term_textview);
        textViewCourse = findViewById(R.id.start_date_textview);
        textView2Course = findViewById(R.id.end_date_textview);
        editStatus = findViewById(R.id.status_textview);
        editMentor = findViewById(R.id.mentor_textview);
        editMentorPhone = findViewById(R.id.mentor_phone_textview);
        editMentorEmail = findViewById(R.id.mentor_email_textview);
        editNote = findViewById(R.id.note_textview);


        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TITLE_COURSE)) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);
            setTitle("Edit Course");
            editCourseTitle.setText(intent.getStringExtra(EXTRA_TITLE_COURSE));
            editAssociatedTerm.setText(intent.getStringExtra(EXTRA_TERM));
            textViewCourse.setText(intent.getStringExtra(EXTRA_DATE_COURSE));
            textView2Course.setText(intent.getStringExtra(EXTRA_DATE2_COURSE));
            editStatus.setText(intent.getStringExtra(EXTRA_STATUS));
            editMentor.setText(intent.getStringExtra(EXTRA_MENTOR));
            editMentorPhone.setText(intent.getStringExtra(EXTRA_PHONE));
            editMentorEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            editNote.setText(intent.getStringExtra(EXTRA_NOTE));
        } else {
            setTitle("Add Course");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);
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

        textViewCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEditCourses.this, date, c
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
        textView2Course.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEditCourses.this, date2, c2
                        .get(Calendar.YEAR), c2.get(Calendar.MONTH),
                        c2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu_alt, menu);
        return true;
    }

    private void saveCourse() {
        String title = editCourseTitle.getText().toString();
        String date = textViewCourse.getText().toString();
        String date2 = textView2Course.getText().toString();
        String associatedTerm = editAssociatedTerm.getText().toString();
        String editCourseStatus = editStatus.getText().toString();
        String editCourseMentor = editMentor.getText().toString();
        String editCoursePhone = editMentorPhone.getText().toString();
        String editCourseEmail = editMentorEmail.getText().toString();
        String editCourseNote = editNote.getText().toString();

        // insert logic for no empty values
        if (title.trim().isEmpty() || date.trim().isEmpty() || date2.trim().isEmpty() ||
                associatedTerm.trim().isEmpty() || editCourseStatus.trim().isEmpty() || editCourseMentor.trim().isEmpty() ||
                editCoursePhone.trim().isEmpty() || editCourseEmail.trim().isEmpty()) {
            Toast.makeText(this, "Please make sure every field is complete", Toast.LENGTH_SHORT).show();
            return;
        }

        // insert logic to validate existing Term


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE_COURSE, title);
        data.putExtra(EXTRA_DATE_COURSE, date);
        data.putExtra(EXTRA_DATE2_COURSE, date2);
        data.putExtra(EXTRA_TERM, associatedTerm);
        data.putExtra(EXTRA_STATUS, editCourseStatus);
        data.putExtra(EXTRA_MENTOR, editCourseMentor);
        data.putExtra(EXTRA_PHONE, editCoursePhone);
        data.putExtra(EXTRA_EMAIL, editCourseEmail);
        data.putExtra(EXTRA_NOTE, editCourseNote);

        setResult(RESULT_OK, data);
        finish();
    }

    private void shareNote() {

        String editCourseNote = editNote.getText().toString();
        String title = editCourseTitle.getText().toString();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, editCourseNote);

        sendIntent.putExtra(Intent.EXTRA_TITLE, title);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void notificationMethod() throws ParseException {

       String startDateNotify = textViewCourse.getText().toString();
       SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss") ;
       Date date1 = sdf.parse(startDateNotify + " 00:00:00");
       long longDate = date1.getTime();


        Intent intent=new Intent(AddEditCourses.this, AlertReceiver.class);
        intent.putExtra("key","Course Starting Today");
        PendingIntent sender = PendingIntent.getBroadcast(AddEditCourses.this,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, longDate, sender);
    }

    private void notificationMethodEnd() throws ParseException {

        String endDateNotify = textView2Course.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss") ;
        Date date1 = sdf.parse(endDateNotify + " 00:00:00");
        long longDate = date1.getTime();


        Intent intent=new Intent(AddEditCourses.this, AlertReceiver.class);
        intent.putExtra("key","Course Ending Today");
        PendingIntent sender = PendingIntent.getBroadcast(AddEditCourses.this,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, longDate, sender);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            case R.id.share:
                shareNote();
                return true;
            case R.id.notificationStart:
                try {
                    notificationMethod();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.notificationEnd:
                try {
                    notificationMethodEnd();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeStart() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textViewCourse.setText(sdf.format(c.getTime()));
    }
    private void changeEnd() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView2Course.setText(sdf.format(c2.getTime()));
    }

}
