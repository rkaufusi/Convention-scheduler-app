package com.example.rkwguapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.rkwguapp.Entities.ConventionEntity;

import java.util.List;

@Dao
public interface ConventionDao {

    @Insert
    void insert(ConventionEntity ConventionEntity);

    @Update
    void update(ConventionEntity ConventionEntity);

    @Delete
    void delete(ConventionEntity ConventionEntity);

    @Query("SELECT * FROM convention_table")
    LiveData<List<ConventionEntity>> getAllConventions();

}
