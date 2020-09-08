package com.example.rkwguapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "subject_table")
public class SubjectEntity {

    @PrimaryKey(autoGenerate = true)
    private int subjectId;
    private String subjectTitle;
    private String associatedConvention;
    private Date subjectStartDate;
    private Date subjectEndDate;
    private int conventionId;

    public SubjectEntity(String subjectTitle, String associatedConvention, Date subjectStartDate, Date subjectEndDate) {
        this.subjectTitle = subjectTitle;
        this.associatedConvention = associatedConvention;
        this.subjectStartDate = subjectStartDate;
        this.subjectEndDate = subjectEndDate;
        this.conventionId = conventionId;
    }

    // subjectId

    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getSubjectId() { return subjectId; }

    //getters

    public String getSubjectTitle() { return subjectTitle; }

    public String getAssociatedConvention() { return associatedConvention; }

    public Date getSubjectStartDate() {
        return subjectStartDate;
    }

    public Date getSubjectEndDate() {
        return subjectEndDate;
    }

    public int getConventionId(){ return conventionId; }

    public void setConventionId(int conventionId){ this.conventionId = conventionId; }

    @Override
    public String toString(){ return subjectTitle; }

}
