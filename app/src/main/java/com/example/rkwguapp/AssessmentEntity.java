package com.example.rkwguapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {

    @PrimaryKey
    private String assessmentsTitle;
    private String associatedCourse;
    private Date assessmentDueDate;

    public AssessmentEntity(String assessmentsTitle, Date assessmentDueDate) {
        this.assessmentsTitle = assessmentsTitle;
        // unsure if needed
        this.associatedCourse = associatedCourse;
        this.assessmentDueDate = assessmentDueDate;

    }

    public String getAssessmentsTitle() {
        return assessmentsTitle;
    }

    public Date getAssessmentDueDate() {
        return assessmentDueDate;
    }
}
