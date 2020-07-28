package com.example.rkwguapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

};

        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
            private TermDao termDao;
            private CourseDao courseDao;
            private AssessmentDao assessmentDao;


        private PopulateDbAsyncTask(AppDatabase db) {
            termDao = db.termDao();
            courseDao = db.courseDao();
            assessmentDao = db.assessmentDao();

        }

            @Override
            protected Void doInBackground(Void... voids) {


            String strDate = "06-01-2020";
            String strDate1 = "01-01-2021";
            String strDate2 = "07-20-2020";
            String strDate3 = "09-05-2020";
            String strDate4 = "08-30-2020";
            String strDate5 = "10-15-2020";

            String strDate6 = "12-31-2020";
            String strDate7 = "06-01-2021";
            String strDate8 = "10-05-2020";
            String strDate9 = "09-30-2020";

                Date date1 = DateConverter.toDateType(strDate);
                Date date2 = DateConverter.toDateType(strDate1);
                Date date3 = DateConverter.toDateType(strDate2);
                Date date4 = DateConverter.toDateType(strDate3);
                Date date5 = DateConverter.toDateType(strDate4);
                Date date6 = DateConverter.toDateType(strDate5);
                Date termEnd = DateConverter.toDateType(strDate6);
                Date termEnd1 = DateConverter.toDateType(strDate7);
                Date courseEnd = DateConverter.toDateType(strDate8);
                Date courseEnd1 = DateConverter.toDateType(strDate9);

                termDao.insert(new TermEntity("Sample 1", date1, termEnd));
                termDao.insert(new TermEntity("Sample 2", date2, termEnd1));

                courseDao.insert(new CourseEntity("c196", "Sample 1", date3, courseEnd, "good", "Steve", "111-111-1111", "email@email.com", "the course note"));
                courseDao.insert(new CourseEntity("c296", "Sample 1", date4, courseEnd1, "good", "Jill", "222-111-1111", "email@email.com", "course note"));

                assessmentDao.insert(new AssessmentEntity("English exam", "C196", date5));
                assessmentDao.insert(new AssessmentEntity("Geometry", "C296", date6));
                return null;
            }
    }

}
