package com.example.rkwguapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TermDao termDao();

}
