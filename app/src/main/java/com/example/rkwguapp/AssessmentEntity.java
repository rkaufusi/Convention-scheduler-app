package com.example.rkwguapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {

    @PrimaryKey
    @NonNull
    private String assessmentsTitle;
    private String associatedCourse;
    private Date assessmentDueDate;

    public AssessmentEntity(String assessmentsTitle, String associatedCourse, Date assessmentDueDate) {
        this.assessmentsTitle = assessmentsTitle;
        this.associatedCourse = associatedCourse;
        this.assessmentDueDate = assessmentDueDate;

    }
    // lone setter

    public void setAssessmentsTitle(String assessmentsTitle) {this.assessmentsTitle = assessmentsTitle; }

    public String getAssociatedCourse() { return associatedCourse; }

    public String getAssessmentsTitle() { return assessmentsTitle; }

    public Date getAssessmentDueDate() { return assessmentDueDate; }
}
