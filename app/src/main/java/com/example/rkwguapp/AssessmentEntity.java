package com.example.rkwguapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String assessmentsTitle;
    private String associatedCourse;
    private Date assessmentDueDate;

    public AssessmentEntity(String assessmentsTitle, String associatedCourse, Date assessmentDueDate) {
        this.assessmentsTitle = assessmentsTitle;
        this.associatedCourse = associatedCourse;
        this.assessmentDueDate = assessmentDueDate;

    }
    // assessment Id

    public void setAssessmentId(int assessmentId) { this.assessmentId = assessmentId; }

    public int getAssessmentId() { return assessmentId; }


    public String getAssociatedCourse() { return associatedCourse; }

    public String getAssessmentsTitle() { return assessmentsTitle; }

    public Date getAssessmentDueDate() { return assessmentDueDate; }
}
