package com.example.rkwguapp;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TermCoursesRelation {
    @Embedded public TermEntity termEntity;
    @Relation(parentColumn = "termTitle", entityColumn = "associatedTerm")
    public List<CourseEntity> courseEntityList;
}
