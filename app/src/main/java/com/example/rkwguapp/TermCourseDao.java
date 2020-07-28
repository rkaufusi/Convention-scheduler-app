package com.example.rkwguapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.rkwguapp.TermCoursesRelation;

import java.util.List;

@Dao
public interface TermCourseDao {

    @Transaction
    @Query("SELECT * FROM term_table")
    public LiveData<List<TermCoursesRelation>> getTermCoursesRelation();
}
