package com.example.rkwguapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rkwguapp.Adapters.SubjectAdapter;
import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.ViewModels.WorkshopViewModel;
import com.example.rkwguapp.ViewModels.SubjectViewModel;
import com.example.rkwguapp.DateConverter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkshopDetail extends AppCompatActivity {

    public static final String EXTRA_ID_A = "com.example.rkwguapp.EXTRA_ID_A";
    public static final String EXTRA_ASSOCIATED_SUBJECT = "com.example.rkwguapp.EXTRA_ASSOCIATED_SUBJECT";
    public static final String EXTRA_WORKSHOP_TITLE = "com.example.rkwguapp.EXTRA_WORKSHOP_TITLE";
    public static final String EXTRA_GOAL = "com.example.rkwguapp.EXTRA_GOAL";
    public static final String EXTRA_ROOM = "com.example.rkwguapp.EXTRA_ROOM";
    public static final String EXTRA_COMPLETE = "com.example.rkwguapp.EXTRA_COMPLETE";
    public static final String EXTRA_START_TS = "com.example.rkwguapp.EXTRA_START_TS";
    public static final String EXTRA_END_TS = "com.example.rkwguapp.EXTRA_END_TS";

    private EditText editTextCourse;
    private EditText editWorkshopTitle;
    private TextView goalDate;
    private Spinner roomSpinner;
    private TextView completeDate;
    private Spinner spinner;
    public SubjectViewModel subjectViewModel;

    public WorkshopViewModel workshopViewModel;

    DatePickerDialog.OnDateSetListener date;
    final Calendar c = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date2;
    final Calendar c2 = Calendar.getInstance();

    SubjectAdapter SubjectAdapter = new SubjectAdapter();
    //SubjectEntity SubjectEntity = new SubjectEntity();
    private ArrayAdapter<SubjectEntity> returnCourses;
    WorkshopEntity workshopEntity;

    //Button AccessTime;
    TextView displayTime;
    TextView endTime;
    int CalendarHour, CalendarMinute;
    String format;
    Calendar calendar;
    TimePickerDialog timepickerdialog;

    String convertToStringStart;
    String convertToStringEnd;

    DateConverter dateConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_detail);

        editWorkshopTitle = findViewById(R.id.edit_text_workshop_title);
        //editTextCourse = findViewById(R.id.edit_text_assess_course);
        goalDate = findViewById(R.id.text_view_goal);
        roomSpinner = findViewById(R.id.edit_text_type);
        completeDate = findViewById(R.id.text_view_complete);
        spinner = findViewById(R.id.subject_spinner);
        displayTime = findViewById(R.id.start_textview);
        endTime = findViewById(R.id.end_textview);

        //String text = spinner.getSelectedItem().toString();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);

        final Intent intent = getIntent();

        final ArrayList<String> termsList = new ArrayList<>();
        termsList.add("Enter Subject");
        final ArrayList<String> roomsList = new ArrayList<>();
        roomsList.add("Enter Room");
        roomsList.add("S1");
        roomsList.add("S2");
        roomsList.add("S3");
        roomsList.add("S4");
        roomsList.add("C1");
        roomsList.add("C2");
        roomsList.add("C3");
        roomsList.add("C4");
        roomsList.add("C5");
        roomsList.add("N1");
        roomsList.add("N2");
        roomsList.add("N3");
        roomsList.add("N4");

        // button Notes
        Button buttonCourses = (Button) findViewById(R.id.notes_button);
        buttonCourses.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent intent = new Intent(WorkshopDetail.this, Note.class);
                //intent.putExtra(WorkshopDetail.EXTRA_WORKSHOP_TITLE, workshopEntity.getWorkshopTitle());
                startActivity(new Intent(WorkshopDetail.this, Note.class));
            }
        });

        // open map
        TextView map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkshopDetail.this, MapOfConvention.class));
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomsList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter1);

        if (intent.hasExtra(EXTRA_WORKSHOP_TITLE)) {
            setTitle("Workshop Details");
            editWorkshopTitle.setText(intent.getStringExtra(EXTRA_WORKSHOP_TITLE));
            goalDate.setText(intent.getStringExtra(EXTRA_GOAL));
            roomSpinner.setSelection(adapter1.getPosition(intent.getStringExtra(EXTRA_ROOM)));
            completeDate.setText(intent.getStringExtra(EXTRA_COMPLETE));
            displayTime.setText(intent.getStringExtra(EXTRA_START_TS));
            endTime.setText(intent.getStringExtra(EXTRA_END_TS));
        } else {
            setTitle("Add Workshop");
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
                new DatePickerDialog(WorkshopDetail.this, date, c
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
        completeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(WorkshopDetail.this, date2, c2
                        .get(Calendar.YEAR), c2.get(Calendar.MONTH),
                        c2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


            // start time
            //AccessTime = (Button)findViewById(R.id.button1);
            displayTime = (TextView)findViewById(R.id.start_textview);

            displayTime.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    calendar = Calendar.getInstance();
                    CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                    CalendarMinute = calendar.get(Calendar.MINUTE);

                    timepickerdialog = new TimePickerDialog(WorkshopDetail.this, new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                    int backToInt = 0;
                                    String concatHours = "";
                                    String strHours = "";
                                    String strMin = "";
                                    String concatMin = "";

                                    if (hourOfDay == 0) {
                                        hourOfDay += 12;
                                        format = "AM";
                                    }
                                    else if (hourOfDay == 12) {
                                        format = "PM";
                                    }
                                    else if (hourOfDay > 12) {
                                        hourOfDay -= 12;
                                        format = "PM";
                                    }
                                    else {
                                        format = "AM";
                                    }

                                    if (hourOfDay < 10) {
                                        strHours = String.valueOf(hourOfDay);
                                        concatHours = "0" + strHours;
                                    } else {
                                        concatHours = String.valueOf(hourOfDay);
                                    }

                                    if (minute < 10) {
                                        strMin = String.valueOf(minute);
                                        concatMin = "0" + strMin;
                                    } else {
                                        concatMin = String.valueOf(minute);
                                    }
                                    //backToInt = Integer.parseInt(concatHours);
                                    displayTime.setText(hourOfDay + ":" + concatMin + " " + format);
                                    convertToStringStart = (concatHours + ":" + concatMin  + " " + format);
                                }
                            }, CalendarHour, CalendarMinute, false);
                    timepickerdialog.show();

                }
            });

            // end time
        endTime = (TextView)findViewById(R.id.end_textview);

        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(WorkshopDetail.this, new TimePickerDialog.OnTimeSetListener() {

                    String concatHours = "";
                    String strHours = "";
                    String strMin = "";
                    String concatMin = "";

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = "AM"; }
                        else if (hourOfDay == 12) {
                            format = "PM"; }
                        else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = "PM"; }
                        else {
                            format = "AM"; }

                        if (hourOfDay < 10) {
                            strHours = String.valueOf(hourOfDay);
                            concatHours = "0" + strHours;
                        } else {
                            concatHours = String.valueOf(hourOfDay); }

                        if (minute < 10) {
                            strMin = String.valueOf(minute);
                            concatMin = "0" + strMin;
                        } else {
                            concatMin = String.valueOf(minute); }

                        //backToInt = Integer.parseInt(concatHours);
                        endTime.setText(hourOfDay + ":" + concatMin + " " + format);
                        convertToStringEnd = (concatHours + ":" + concatMin  + " " + format);
                        //endTime.setText(hourOfDay + ":" + minute);
                    }
                }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });

/*
        final TextView eReminderTime = findViewById(R.id.start_textview);

        eReminderTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(WorkshopDetail.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       if (selectedHour > 12) {
                            eReminderTime.setText( (selectedHour -= 12 ) + ":" + selectedMinute + " PM");


                        }
                        eReminderTime.setText( selectedHour + ":" + selectedMinute + " AM");
                    }
                }, hour, minute, false);
                //mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
*/
        //populate spinner
        subjectViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(SubjectViewModel.class);
        subjectViewModel.getAllSubjects().observe(this, new Observer<List<SubjectEntity>>() {
            @Override
            public void onChanged(@Nullable final List<SubjectEntity> courses) {
                for (SubjectEntity p : courses) {
                    termsList.add(p.getSubjectTitle());
                    spinner.setSelection(adapter.getPosition(intent.getStringExtra(EXTRA_ASSOCIATED_SUBJECT)));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void notificationMethod() throws ParseException {

        String assessDate = goalDate.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date1 = sdf.parse(assessDate + " 00:00:00");
        long longDate = date1.getTime();

        Intent intent=new Intent(WorkshopDetail.this, AlertReceiver.class);
        intent.putExtra("key","Your workshop is today");
        PendingIntent sender = PendingIntent.getBroadcast(WorkshopDetail.this,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, longDate, sender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.workshop_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void deleteCourse() {
            workshopViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkshopViewModel.class);
            workshopViewModel.getAllWorkshops().observe(this, new Observer<List<WorkshopEntity>>() {
                @Override
                public void onChanged(@Nullable final List<WorkshopEntity> words) {
                    List<WorkshopEntity> filteredWords = new ArrayList<>();
                    for (WorkshopEntity p : words){
                        if (p.getWorkshopTitle().equals(editWorkshopTitle.getText().toString())) {

                            workshopViewModel.delete(p);
                            Toast.makeText(getApplicationContext(), "Workshop Deleted", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(WorkshopDetail.this, Workshop.class);
                            startActivity(i);
                        }
                    }
                }
            });
    }

    private void saveAssessment() throws ParseException {

        String title = editWorkshopTitle.getText().toString();
        String course2 = spinner.getSelectedItem().toString();
        String goal = goalDate.getText().toString();
        String type = roomSpinner.getSelectedItem().toString();
        String endDate = completeDate.getText().toString();

        // gets both the start date plus start time
        if (convertToStringStart == null) {
            convertToStringStart = displayTime.getText().toString();
        }
        String datePlusTime = goal + " " + convertToStringStart;

        if (convertToStringEnd == null) {
            convertToStringEnd = endTime.getText().toString();
        }

        String datePlusTimeEnd = endDate + " " + convertToStringEnd;

        // logic for no empty fields
        if (title.trim().isEmpty() || course2.trim().isEmpty() || goal.trim().isEmpty() ||  type.trim().isEmpty()) {
            Toast.makeText(this, "Please fill every field", Toast.LENGTH_SHORT).show();
            return;
        }

        Date d = DateConverter.toDateType(goal);
        Date d2 = DateConverter.toDateType(endDate);

        if (d.compareTo(d2) > 0) {
            Toast.makeText(this, "Start Date must be before End Date", Toast.LENGTH_SHORT).show();
            return;
        }

        Date dT = DateConverter.toTimeStamp(datePlusTime);
        Date dT2 = DateConverter.toTimeStamp(datePlusTimeEnd);

        if (dT.compareTo(dT2) > 0) {
            Toast.makeText(this, "Start Time must be before End Time", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_WORKSHOP_TITLE, title);
        data.putExtra(EXTRA_ASSOCIATED_SUBJECT, course2);
        data.putExtra(EXTRA_GOAL, goal);
        data.putExtra(EXTRA_ROOM, type);

        data.putExtra(EXTRA_START_TS, datePlusTime);
        data.putExtra(EXTRA_COMPLETE, endDate);
        data.putExtra(EXTRA_END_TS, datePlusTimeEnd);

        int id = getIntent().getIntExtra(EXTRA_ID_A, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID_A, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_workshop:
                try {
                    saveAssessment();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.delete:
                deleteCourse();
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
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        goalDate.setText(sdf.format(c.getTime()));
    }
    private void changeEnd() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        completeDate.setText(sdf.format(c2.getTime()));
    }

}