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

import com.example.rkwguapp.Adapters.WorkshopAdapter;
import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.ViewModels.WorkshopViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Workshop extends AppCompatActivity {
    public static final int ADD_ASSESS_REQUEST = 5;
    public static final int EDIT_ASSESS_REQUEST = 6;

    private WorkshopViewModel workshopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        FloatingActionButton buttonAddAsses = findViewById(R.id.button_add_assess);
        buttonAddAsses.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent intent = new Intent(Workshop.this, WorkshopDetail.class);
                startActivityForResult(intent, ADD_ASSESS_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_assess);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final WorkshopAdapter WorkshopAdapter = new WorkshopAdapter();
        recyclerView.setAdapter(WorkshopAdapter);

       // workshopViewModel = ViewModelProvider.of(this).get(workshopViewModel.class);
        workshopViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkshopViewModel.class);
        workshopViewModel.getAllWorkshops().observe(this, new Observer<List<WorkshopEntity>>() {
            @Override
            public void onChanged(@Nullable List<WorkshopEntity> workshopEntities) {
               WorkshopAdapter.setWorkshopEntityList(workshopEntities);
            }
        });

        // edit assessment
        WorkshopAdapter.setOnItemClickListener(new WorkshopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WorkshopEntity WorkshopEntity) {
                Intent intent = new Intent(Workshop.this, WorkshopDetail.class);
                intent.putExtra(WorkshopDetail.EXTRA_ID_A, WorkshopEntity.getWorkshopId());
                intent.putExtra(WorkshopDetail.EXTRA_WORKSHOP_TITLE, WorkshopEntity.getWorkshopTitle());
                intent.putExtra(WorkshopDetail.EXTRA_ASSOCIATED_SUBJECT, WorkshopEntity.getAssociatedSubject());
                intent.putExtra(WorkshopDetail.EXTRA_ROOM, WorkshopEntity.getRoom());
                // convert date to string to transfer to next activity
                String timestampPattern = "hh:mm aa";
                SimpleDateFormat tp = new SimpleDateFormat(timestampPattern);
                String strTimestamp = tp.format(WorkshopEntity.getStartTime());
                intent.putExtra(WorkshopDetail.EXTRA_START_TS, strTimestamp);

                String endStrTimestamp = tp.format(WorkshopEntity.getEndTime());
                intent.putExtra(WorkshopDetail.EXTRA_END_TS, endStrTimestamp);

                String pattern = "MM-dd-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                String date = sdf.format(WorkshopEntity.getWorkshopDate());
                intent.putExtra(WorkshopDetail.EXTRA_GOAL, date);
                // added a second date for assessments
                String date1 = sdf.format((WorkshopEntity.getWorkshopCompleteDate()));
                intent.putExtra(WorkshopDetail.EXTRA_COMPLETE, date1);

                startActivityForResult(intent, EDIT_ASSESS_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ASSESS_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(WorkshopDetail.EXTRA_WORKSHOP_TITLE);
            String course = data.getStringExtra(WorkshopDetail.EXTRA_ASSOCIATED_SUBJECT);
            String type = data.getStringExtra(WorkshopDetail.EXTRA_ROOM);
            String goal = data.getStringExtra(WorkshopDetail.EXTRA_GOAL);
            String complete = data.getStringExtra(WorkshopDetail.EXTRA_COMPLETE);
            String startTime = data.getStringExtra(WorkshopDetail.EXTRA_START_TS);
            String endTime = data.getStringExtra((WorkshopDetail.EXTRA_END_TS));

            // convert to date
            Date date = DateConverter.toDateType(goal);
            Date dateFinish = DateConverter.toDateType(complete);
            Date ts = DateConverter.toTimeStamp(startTime);
            Date end = DateConverter.toTimeStamp(endTime);

            WorkshopEntity WorkshopEntity = new WorkshopEntity(title, course, type, date, dateFinish, ts, end);
            workshopViewModel.insert(WorkshopEntity);

            Toast.makeText(this, "Workshop Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_ASSESS_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(WorkshopDetail.EXTRA_ID_A, -1);
            String title = data.getStringExtra(WorkshopDetail.EXTRA_WORKSHOP_TITLE);
            String course = data.getStringExtra(WorkshopDetail.EXTRA_ASSOCIATED_SUBJECT);
            String type = data.getStringExtra(WorkshopDetail.EXTRA_ROOM);
            String goal = data.getStringExtra(WorkshopDetail.EXTRA_GOAL);
            String complete = data.getStringExtra(WorkshopDetail.EXTRA_COMPLETE);
            String startTime = data.getStringExtra(WorkshopDetail.EXTRA_START_TS);
            String endTime = data.getStringExtra((WorkshopDetail.EXTRA_END_TS));

            Date date = DateConverter.toDateType(goal);
            Date dateFinish = DateConverter.toDateType(complete);
            Date ts = DateConverter.toTimeStamp(startTime);
            Date end = DateConverter.toTimeStamp(endTime);

            WorkshopEntity WorkshopEntity = new WorkshopEntity(title, course, type, date, dateFinish, ts, end);
            WorkshopEntity.setWorkshopId(id);
            workshopViewModel.update(WorkshopEntity);

            Toast.makeText(this, "Workshop Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Workshop not saved", Toast.LENGTH_SHORT).show();
        }
    }
}