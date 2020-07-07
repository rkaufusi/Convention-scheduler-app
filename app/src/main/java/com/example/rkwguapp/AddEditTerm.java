package com.example.rkwguapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.Snackbar;
//import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditTerm extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.rkwguapp.EXTRA_TITLE";
    public static final String EXTRA_DATE = "com.example.rkwguapp.EXTRA_DATE";
    public static final String EXTRA_DATE2 = "com.example.rkwguapp.EXTRA_DATE2";

    final Calendar c = Calendar.getInstance();
    final Calendar c2 = Calendar.getInstance();
    TextView textView;
    TextView textView2;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;
    TermViewModel termViewModel;

    private EditText editTermTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        editTermTitle = findViewById(R.id.edit_term_title);
        textView = findViewById(R.id.start_date_textview);
        textView2 = findViewById(R.id.end_date_textview);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TITLE)) {
            setTitle("Edit Term");
            editTermTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            textView.setText(intent.getStringExtra(EXTRA_DATE));
            textView2.setText(intent.getStringExtra(EXTRA_DATE2));
        } else {
            setTitle("Add Term");
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

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEditTerm.this, date, c
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
        textView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEditTerm.this, date2, c2
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

    private void changeStart() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(c.getTime()));
    }
    private void changeEnd() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView2.setText(sdf.format(c2.getTime()));
    }

}
