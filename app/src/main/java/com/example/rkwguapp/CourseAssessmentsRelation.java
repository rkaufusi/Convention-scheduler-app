package com.example.rkwguapp;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CourseAssessmentsRelation {
    @Embedded
    public CourseEntity courseEntity;
    @Relation(parentColumn = "courseTitle", entityColumn = "associatedCourse")
    public List<AssessmentEntity> assessmentEntityList;
}
