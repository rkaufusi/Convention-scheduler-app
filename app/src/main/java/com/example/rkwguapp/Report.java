package com.example.rkwguapp;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.Adapters.ReportAdapter;
import com.example.rkwguapp.Adapters.SubjectDetailAdapter;
import com.example.rkwguapp.Adapters.WorkshopAdapter;
import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.ViewModels.WorkshopViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report extends AppCompatActivity {

    ReportAdapter reportAdapter;
    WorkshopViewModel workshopViewModel;
    WorkshopAdapter workshopAdapter;
    int numberOfWorkshops;
    SubjectDetailAdapter subjectDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        getTheWorkshops();

        }

    public void getTheWorkshops() {

        final TableLayout layout = findViewById(R.id.table_main);
        TableRow row = new TableRow(Report.this);
        TextView view = new TextView(Report.this);
        view.setText("Workshop ");
        row.addView(view);
        TextView view1 = new TextView(Report.this);
        view1.setText("Subject ");
        row.addView(view1);
        TextView view2 = new TextView(Report.this);
        view2.setText("Room ");
        row.addView(view2);
        TextView view3 = new TextView(Report.this);
        view3.setText("Date ");
        row.addView(view3);

        TextView view4 = new TextView(Report.this);
        view4.setText("Start ");
        row.addView(view4);
        TextView view5 = new TextView(Report.this);
        view5.setText("End ");
        row.addView(view5);
        TextView view6 = new TextView(Report.this);
        view6.setText("End Time ");
        row.addView(view6);
        layout.addView(row);

        workshopViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkshopViewModel.class);
        workshopViewModel.getAllWorkshops().observe(this, new Observer<List<WorkshopEntity>>() {
            @Override
            public void onChanged(@Nullable List<WorkshopEntity> workshopEntities) {
                List<WorkshopEntity> workshop = new ArrayList<>();

                for (WorkshopEntity p : workshopEntities) {
                     String wTitle = p.getWorkshopTitle();
                     String xSubject = p.getAssociatedSubject();
                     String wRoom = p.getRoom();
                     Date wDate = p.getWorkshopDate();
                     Date wStartTime = p.getStartTime();
                     Date wEndDate = p.getWorkshopCompleteDate();
                     Date wEndTime = p.getEndTime();

                    workshop.add(p);

                    numberOfWorkshops = workshop.size();
                    // put variables into table
                    TableRow loopedRow = new TableRow(Report.this);
                    TextView loopedView = new TextView(Report.this);
                    loopedView.setText(wTitle + "  ");
                    loopedRow.addView(loopedView);
                    TextView loopedView1 = new TextView(Report.this);
                    loopedView1.setText(xSubject + "  ");
                    loopedRow.addView(loopedView1);
                    TextView loopedView2 = new TextView(Report.this);
                    loopedView2.setText(wRoom + "  ");
                    loopedRow.addView(loopedView2);
                    TextView loopedView3 = new TextView(Report.this);
                    loopedView3.setText(wDate.toString() + "  ");
                    loopedRow.addView(loopedView3);

                    TextView loopedView4 = new TextView(Report.this);
                    loopedView4.setText(wStartTime.toString() + "  ");
                    loopedRow.addView(loopedView4);
                    TextView loopedView5 = new TextView(Report.this);
                    loopedView5.setText(wEndDate.toString() + "  ");
                    loopedRow.addView(loopedView5);
                    TextView loopedView6 = new TextView(Report.this);
                    loopedView6.setText(wEndTime.toString() + "  ");
                    loopedRow.addView(loopedView6);
                    layout.addView(loopedRow);
                }
            }
        });
    }

}
