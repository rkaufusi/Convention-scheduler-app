package com.example.rkwguapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }
// new code
private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

};

        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
            private TermDao termDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            termDao = db.termDao();
        }
            @Override
            protected Void doInBackground(Void... voids) {
            // new
                Date date;
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy");

                termDao.insert(new TermEntity("Title 1", new Date(2020), new Date(2020)));
                termDao.insert(new TermEntity("Title 2", new Date(2020), new Date(2020)));
                termDao.insert(new TermEntity("Title 3", new Date(2021), new Date(2021)));
                return null;
            }
    }

}
