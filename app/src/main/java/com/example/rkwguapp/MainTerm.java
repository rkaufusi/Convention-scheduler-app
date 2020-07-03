package com.example.rkwguapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Date;
import java.util.List;

public class MainTerm extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_term);

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTerm.this, AddTerm.class);
                        startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        final TermAdapter termAdapter = new TermAdapter();
        recyclerView.setAdapter(termAdapter);

        termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable List<TermEntity> termEntities) {
                termAdapter.setTermEntities(termEntities);
              //  Toast.makeText(MainTerm.this,"test", Toast.LENGTH_LONG).show();

            }
        });


    }


    // don't think it makes it here took off @Nullable
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddTerm.EXTRA_TITLE);
            String date = data.getStringExtra(AddTerm.EXTRA_DATE);
            String date2 = data.getStringExtra(AddTerm.EXTRA_DATE2);

                Date start = DateConverter.toDateType(date);
                Date end = DateConverter.toDateType(date2);

                TermEntity term = new TermEntity(title, start, end);
                termViewModel.insert(term);


                Toast.makeText(this, "Term Saved", Toast.LENGTH_SHORT);


        } else { Toast.makeText(this, "Term Not Saved", Toast.LENGTH_SHORT);}
    }
}