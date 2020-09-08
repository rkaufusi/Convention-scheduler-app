package com.example.rkwguapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rkwguapp.Entities.NotesEntity;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void insert(NotesEntity NotesEntity);

    @Update
    void update(NotesEntity NotesEntity);

    @Delete
    void delete(NotesEntity NotesEntity);

    @Query("SELECT * FROM notes_table")
    LiveData<List<NotesEntity>> getAllNotes();

}
