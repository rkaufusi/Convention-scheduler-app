package com.example.rkwguapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rkwguapp.Entities.WorkshopEntity;

import java.util.List;

@Dao
public interface WorkshopDao {

    @Insert
    void insert(WorkshopEntity WorkshopEntity);

    @Update
    void update(WorkshopEntity WorkshopEntity);

    @Delete
    void delete(WorkshopEntity WorkshopEntity);

    @Query("SELECT * FROM workshop_table")
    LiveData<List<WorkshopEntity>> getAllWorkshops();

}
