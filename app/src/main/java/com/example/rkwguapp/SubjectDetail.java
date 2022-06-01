package com.example.rkwguapp;

import android.app.AlarmManager;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.Adapters.SubjectDetailAdapter;
import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.Entities.ConventionEntity;
import com.example.rkwguapp.ViewModels.WorkshopViewModel;
import com.example.rkwguapp.ViewModels.SubjectViewModel;
import com.example.rkwguapp.ViewModels.ConventionViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SubjectDetail extends AppCompatActivity {
    public static final String EXTRA_ID_C = "com.example.rkwguapp.EXTRA_ID_C";
    public static final String EXTRA_TITLE_SUBJECT = "com.example.rkwguapp.EXTRA_TITLE_SUBJECT";
    public static final String EXTRA_DATE_SUBJECT = "com.example.rkwguapp.EXTRA_DATE_SUBJECT";
    public static final String EXTRA_DATE2_SUBJECT = "com.example.rkwguapp.EXTRA_DATE2_SUBJECT";
    public static final String EXTRA_ASSOCIATED_CONVENTION = "com.example.rkwguapp.EXTRA_ASSOCIATED_CONVENTION";

    public static final String CHANNEL_ID = "Start Alert";

    final Calendar c = Calendar.getInstance();
    final Calendar c2 = Calendar.getInstance();

    TextView textViewCourse;
    TextView textView2Course;

    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;

    private EditText editSubjectTitle;
    private EditText editassociatedConvention;

    private TextView mills;

    ConventionEntity ConventionEntity;

    public WorkshopViewModel workshopViewModel;
    public SubjectViewModel subjectViewModel;
    private ConventionViewModel conventionViewModel;
    public static int numberOfAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        editSubjectTitle = findViewById(R.id.edit_subject_title);
        editassociatedConvention = findViewById(R.id.associated_convention);
        textViewCourse = findViewById(R.id.start_date_textview);
        textView2Course = findViewById(R.id.end_date_textview);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TITLE_SUBJECT)) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);
            setTitle("Subject Details");
            editSubjectTitle.setText(intent.getStringExtra(EXTRA_TITLE_SUBJECT));
            editassociatedConvention.setText(intent.getStringExtra(EXTRA_ASSOCIATED_CONVENTION));
            textViewCourse.setText(intent.getStringExtra(EXTRA_DATE_SUBJECT));
            textView2Course.setText(intent.getStringExtra(EXTRA_DATE2_SUBJECT));
        } else {
            setTitle("Add Subject");
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
                new DatePickerDialog(SubjectDetail.this, date, c
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
                new DatePickerDialog(SubjectDetail.this, date2, c2
                        .get(Calendar.YEAR), c2.get(Calendar.MONTH),
                        c2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//populate recylerView
        final RecyclerView recyclerView = findViewById(R.id.associated_workshops);
        //final SubjectAdapter adapter = new SubjectAdapter();
        final SubjectDetailAdapter adapter = new SubjectDetailAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //subjectViewModel = new ViewModelProvider(this).get(subjectViewModel.class);
        //subjectViewModel.getAllSubjects().observe(this, new Observer<List<SubjectEntity>>() {

        workshopViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkshopViewModel.class);
        workshopViewModel.getAllWorkshops().observe(this, new Observer<List<WorkshopEntity>>() {
            @Override
            public void onChanged(@Nullable final List<WorkshopEntity> workshops) {
                List<WorkshopEntity> filteredAssessments = new ArrayList<>();

                for (WorkshopEntity p : workshops) {
                    String currentCourse = editSubjectTitle.getText().toString();
                    String associatedWorkshop = p.getAssociatedSubject();

                    if (associatedWorkshop.equals(currentCourse)) {
                        filteredAssessments.add(p);
                        adapter.setCourses(filteredAssessments);
                    }
                    numberOfAssessments = filteredAssessments.size();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_subject_menu, menu);
        return true;
    }

    private void deleteCourse() {
        if(numberOfAssessments == 0) {
            subjectViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(SubjectViewModel.class);
            subjectViewModel.getAllSubjects().observe(this, new Observer<List<SubjectEntity>>() {
                @Override
                public void onChanged(@Nullable final List<SubjectEntity> words) {
                    List<SubjectEntity> filteredWords = new ArrayList<>();
                    for (SubjectEntity p : words){
                        if (p.getSubjectTitle().equals(editSubjectTitle.getText().toString())) {

                            subjectViewModel.delete(p);
                            Toast.makeText(getApplicationContext(), "Course Deleted", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SubjectDetail.this, Subject.class);
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

    private void saveCourse() {
        String title = editSubjectTitle.getText().toString();
        String date = textViewCourse.getText().toString();
        String date2 = textView2Course.getText().toString();
        String associatedConvention = editassociatedConvention.getText().toString();

        // insert logic for no empty values
        if (title.trim().isEmpty() || date.trim().isEmpty() || date2.trim().isEmpty() ||
                associatedConvention.trim().isEmpty()) {
            Toast.makeText(this, "Please make sure every field is complete", Toast.LENGTH_SHORT).show();
            return;
        }

        Date d = DateConverter.toDateType(date);
        Date d2 = DateConverter.toDateType(date2);

        if (d.compareTo(d2) > 0) {
            Toast.makeText(this, "Start Date must be before End Date", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE_SUBJECT, title);
        data.putExtra(EXTRA_DATE_SUBJECT, date);
        data.putExtra(EXTRA_DATE2_SUBJECT, date2);
        data.putExtra(EXTRA_ASSOCIATED_CONVENTION, associatedConvention);

        int id = getIntent().getIntExtra(EXTRA_ID_C, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID_C, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    private void notificationMethod() throws ParseException {

       String startDateNotify = textViewCourse.getText().toString();
       SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss") ;
       Date date1 = sdf.parse(startDateNotify + " 00:00:00");
       long longDate = date1.getTime();


        Intent intent=new Intent(SubjectDetail.this, AlertReceiver.class);
        intent.putExtra("key","Subject Starting Today");
        PendingIntent sender = PendingIntent.getBroadcast(SubjectDetail.this,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, longDate, sender);
    }

    private void notificationMethodEnd() throws ParseException {

        String endDateNotify = textView2Course.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss") ;
        Date date1 = sdf.parse(endDateNotify + " 00:00:00");
        long longDate = date1.getTime();

        Intent intent=new Intent(SubjectDetail.this, AlertRecieverEnd.class);
        intent.putExtra("key","Subject Ending Today");
        PendingIntent sender = PendingIntent.getBroadcast(SubjectDetail.this,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, longDate, sender);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_subject:
                saveCourse();
                return true;
            case R.id.delete:
                deleteCourse();
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
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textViewCourse.setText(sdf.format(c.getTime()));
    }
    private void changeEnd() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView2Course.setText(sdf.format(c2.getTime()));
    }
}
