package com.example.rkwguapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTerm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText editTermTitle;
    private TextView editStartDate;
    private TextView editEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        editTermTitle = findViewById(R.id.edit_term_title);
        editStartDate = findViewById(R.id.start_date_textview);
        editEndDate = findViewById(R.id.end_date_textview);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_button);
        setTitle("Add Term");

        Button button = (Button) findViewById(R.id.start_date_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment termStartPicker = new TermStartFragment();
                termStartPicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button button1 = (Button) findViewById(R.id.end_date_button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment termEndPicker = new TermEndFragment();
                termEndPicker.show(getSupportFragmentManager(), "end date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String termStartString = DateFormat.getDateInstance().format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.start_date_textview);
        textView.setText(termStartString);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
