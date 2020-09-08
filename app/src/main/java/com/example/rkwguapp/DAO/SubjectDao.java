package com.example.rkwguapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rkwguapp.Entities.SubjectEntity;

import java.util.List;

@Dao
public interface SubjectDao {

    @Insert
    void insert(SubjectEntity SubjectEntity);

    @Update
    void update(SubjectEntity SubjectEntity);

    @Delete
    void delete(SubjectEntity SubjectEntity);

    @Query("SELECT * FROM subject_table")
    LiveData<List<SubjectEntity>> getAllSubjects();

//was LiveData
   // @Query("SELECT * FROM course_table WHERE conventionId = :conventionId")
   // List<SubjectEntity> getTermCourses(int conventionId);


}
