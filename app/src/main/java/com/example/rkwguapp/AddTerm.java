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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTerm extends AppCompatActivity {
public static final String EXTRA_TITLE = "com.example.rkwguapp.EXTRA_TITLE";
    public static final String EXTRA_DATE = "com.example.rkwguapp.EXTRA_DATE";
    public static final String EXTRA_DATE2 = "com.example.rkwguapp.EXTRA_DATE2";

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    TextView edittext;
    TextView edittext2;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;

    private EditText editTermTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
       // Toolbar toolbar = findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

    //    FloatingActionButton fab = findViewById(R.id.fab);
    //    fab.setOnClickListener(new View.OnClickListener() {
   //         @Override
    //        public void onClick(View view) {
   //             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
   //                     .setAction("Action", null).show();
   //         }
  //      });




        edittext = findViewById(R.id.start_date_textview);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Intent intent=new Intent(AddTerm.this,TermStartFragment.class);
                intent.putExtra("key",sdf.format(myCalendar.getTime()));
                PendingIntent sender= PendingIntent.getBroadcast(AddTerm.this,0,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                long trigger=myCalendar.getTimeInMillis();
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                updateLabel();
            }

        };
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTerm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edittext2 = findViewById(R.id.end_date_textview);
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Intent intent=new Intent(AddTerm.this,TermEndFragment.class);
                intent.putExtra("key",sdf.format(myCalendar2.getTime()));
                PendingIntent sender= PendingIntent.getBroadcast(AddTerm.this,1,intent,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                long trigger=myCalendar2.getTimeInMillis();
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                updateLabel2();
            }

        };

        edittext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTerm.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel2() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext2.setText(sdf.format(myCalendar2.getTime()));
    }



    //   @Override
 //   public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
 //       getMenuInflater().inflate(R.menu.menu_main, menu);
 //       return true;
 //   }

  //  @Override
  //  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    //    if (id == R.id.action_settings) {
    //        return true;
    //    }

    //    return super.onOptionsItemSelected(item);
   // }


}
