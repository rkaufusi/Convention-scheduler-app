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
public interface TermDao {

    @Insert
    void insert(TermEntity termEntity);

    @Update
    void update(TermEntity termEntity);

    @Delete
    void delete(TermEntity termEntity);

    @Query("SELECT * FROM term_table")
    LiveData<List<TermEntity>> getAllTerms();

    @Transaction
    @Query("SELECT * FROM term_table")
    public List<TermCoursesRelation> getTermCoursesRelation();
}
