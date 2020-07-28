package com.example.rkwguapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insert(CourseEntity courseEntity);

    @Update
    void update(CourseEntity courseEntity);

    @Delete
    void delete(CourseEntity courseEntity);

    @Query("SELECT * FROM course_table")
    LiveData<List<CourseEntity>> getAllCourses();

//was LiveData
   // @Query("SELECT * FROM course_table WHERE termId = :termId")
   // List<CourseEntity> getTermCourses(int termId);

    @Transaction
    @Query("SELECT * FROM course_table")
    public List<CourseAssessmentsRelation> getCourseAssessmentRelation();
}
