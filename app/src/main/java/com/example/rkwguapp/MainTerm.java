package com.example.rkwguapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainTerm extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_term);

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTerm.this, AddEditTerm.class);
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
                termAdapter.submitList(termEntities);

            }
        });
// Delete term by swiping. Possible add "delete All Terms" later
        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                termViewModel.delete(termAdapter.getTermAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainTerm.this, "Term Deleted", Toast.LENGTH_SHORT).show();
            }
        })).attachToRecyclerView(recyclerView);

        // edit term
        termAdapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TermEntity termEntity) {
                Intent intent = new Intent(MainTerm.this, AddEditTerm.class);
                intent.putExtra(AddEditTerm.EXTRA_TITLE, termEntity.getTermTitle());
              // String formatter
                String pattern = "MM-dd-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                String date = sdf.format(termEntity.getTermStartDate());
                intent.putExtra(AddEditTerm.EXTRA_DATE, date);
                String date2 = sdf.format((termEntity.getTermEndDate()));
                intent.putExtra(AddEditTerm.EXTRA_DATE2, date2);

               // intent.putExtra(AddEditTerm.EXTRA_ASSOCIATED_COURSES, TermDao.getTermCoursesRelation());
                startActivityForResult(intent, EDIT_TERM_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTerm.EXTRA_TITLE);
            String date = data.getStringExtra(AddEditTerm.EXTRA_DATE);
            String date2 = data.getStringExtra(AddEditTerm.EXTRA_DATE2);

                Date start = DateConverter.toDateType(date);
                Date end = DateConverter.toDateType(date2);

                TermEntity term = new TermEntity(title, start, end);
                termViewModel.insert(term);

                Toast.makeText(this, "Term Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddEditTerm.EXTRA_TITLE);
            String date = data.getStringExtra(AddEditTerm.EXTRA_DATE);
            String date2 = data.getStringExtra(AddEditTerm.EXTRA_DATE2);

            Date start = DateConverter.toDateType(date);
            Date end = DateConverter.toDateType(date2);

            TermEntity term = new TermEntity(title, start, end);
            //term.setTermTitle(title);

            termViewModel.update(term);

            Toast.makeText(this, "Term Updated", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Term Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}