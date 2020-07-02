package com.example.rkwguapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTerm extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.rkwguapp.EXTRA_TITLE";
    public static final String EXTRA_DATE = "com.example.rkwguapp.EXTRA_DATE";
    public static final String EXTRA_DATE2 = "com.example.rkwguapp.EXTRA_DATE2";

    final Calendar c = Calendar.getInstance();
    final Calendar c2 = Calendar.getInstance();
    TextView textView;
    TextView textView2;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;

    private EditText editTermTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        // close button coming soon
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);
        setTitle("Add Term");


        textView = findViewById(R.id.start_date_textview);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Intent intent=new Intent(AddTerm.this,TermStartFragment.class);
                intent.putExtra("key",sdf.format(c.getTime()));
                PendingIntent sender= PendingIntent.getBroadcast(AddTerm.this,0,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                long trigger=c.getTimeInMillis();
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                updateLabel();
            }

        };
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTerm.this, date, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        textView2 = findViewById(R.id.end_date_textview);

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c2.set(Calendar.YEAR, year);
                c2.set(Calendar.MONTH, monthOfYear);
                c2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
               // Intent intent=new Intent(AddTerm.this,TermEndFragment.class);
               // intent.putExtra("key",sdf.format(c2.getTime()));
               // PendingIntent sender= PendingIntent.getBroadcast(AddTerm.this,1,intent,0);
               // AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
               // long trigger=c2.getTimeInMillis();
               // alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                updateLabel2();
            }

        };

        textView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTerm.this, date2, c2
                        .get(Calendar.YEAR), c2.get(Calendar.MONTH),
                        c2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        return true;
    }

    private void saveTerm() {
        String title = editTermTitle.getText().toString();
        String date = textView.getText().toString();
        String date2 = textView2.getText().toString();

        // insert logic for no empty values
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_DATE2, date2);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                saveTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(c.getTime()));
    }
    private void updateLabel2() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView2.setText(sdf.format(c2.getTime()));
    }


}
