package com.example.rkwguapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class AddTerm extends AppCompatActivity {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}