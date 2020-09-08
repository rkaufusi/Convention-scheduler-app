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

import com.example.rkwguapp.Adapters.ConventionAdapter;
import com.example.rkwguapp.Entities.ConventionEntity;
import com.example.rkwguapp.ViewModels.ConventionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Convention extends AppCompatActivity {
    public static final int ADD_CONVENTION_REQUEST = 1;
    public static final int EDIT_CONVENTION_REQUEST = 2;

    public static int sizeOfSubjects;
    private ConventionViewModel conventionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convention);

        FloatingActionButton buttonAddConvention = findViewById(R.id.button_add_convention);
        buttonAddConvention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Convention.this, ConventionDetail.class);
                startActivityForResult(intent, ADD_CONVENTION_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ConventionAdapter ConventionAdapter = new ConventionAdapter();
        recyclerView.setAdapter(ConventionAdapter);

        conventionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ConventionViewModel.class);
        conventionViewModel.getAllConventions().observe(this, new Observer<List<ConventionEntity>>() {
            @Override
            public void onChanged(@Nullable List<ConventionEntity> conventionEntities) {
                ConventionAdapter.submitList(conventionEntities);

            }
        });

        // edit Convention
        ConventionAdapter.setOnItemClickListener(new ConventionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ConventionEntity ConventionEntity) {
                Intent intent = new Intent(Convention.this, ConventionDetail.class);
                intent.putExtra(ConventionDetail.EXTRA_ID, ConventionEntity.getId());
                intent.putExtra(ConventionDetail.EXTRA_TITLE, ConventionEntity.getConventionTitle());

                String pattern = "MM-dd-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                String date = sdf.format(ConventionEntity.getConventionStartDate());
                intent.putExtra(ConventionDetail.EXTRA_DATE, date);
                String date2 = sdf.format((ConventionEntity.getConventionEndDate()));
                intent.putExtra(ConventionDetail.EXTRA_DATE2, date2);

                startActivityForResult(intent, EDIT_CONVENTION_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_CONVENTION_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(ConventionDetail.EXTRA_TITLE);
            String date = data.getStringExtra(ConventionDetail.EXTRA_DATE);
            String date2 = data.getStringExtra(ConventionDetail.EXTRA_DATE2);

                Date start = DateConverter.toDateType(date);
                Date end = DateConverter.toDateType(date2);

                ConventionEntity convention = new ConventionEntity(title, start, end);
                conventionViewModel.insert(convention);

                Toast.makeText(this, "Convention Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_CONVENTION_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(ConventionDetail.EXTRA_ID, -1);
            String title = data.getStringExtra(ConventionDetail.EXTRA_TITLE);
            String date = data.getStringExtra(ConventionDetail.EXTRA_DATE);
            String date2 = data.getStringExtra(ConventionDetail.EXTRA_DATE2);

            Date start = DateConverter.toDateType(date);
            Date end = DateConverter.toDateType(date2);

            ConventionEntity convention = new ConventionEntity(title, start, end);
            convention.setId(id);
            conventionViewModel.update(convention);

            Toast.makeText(this, "Convention Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Convention Not Saved", Toast.LENGTH_SHORT).show();
       }
    }
}