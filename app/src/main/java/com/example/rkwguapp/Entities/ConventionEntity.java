package com.example.rkwguapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "convention_table")
public class ConventionEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String conventionTitle;
    private Date conventionStartDate;
    private Date conventionEndDate;


    public ConventionEntity(String conventionTitle, Date conventionStartDate, Date conventionEndDate) {
        this.conventionTitle = conventionTitle;
        this.conventionStartDate = conventionStartDate;
        this.conventionEndDate = conventionEndDate;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public String getConventionTitle() { return conventionTitle; }

    public Date getConventionStartDate() { return conventionStartDate; }

    public Date getConventionEndDate() { return conventionEndDate; }

}
