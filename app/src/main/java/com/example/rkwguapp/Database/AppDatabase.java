package com.example.rkwguapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.rkwguapp.DAO.NotesDao;
import com.example.rkwguapp.DAO.WorkshopDao;
import com.example.rkwguapp.DAO.SubjectDao;
import com.example.rkwguapp.DAO.ConventionDao;
import com.example.rkwguapp.DateConverter;
import com.example.rkwguapp.Entities.NotesEntity;
import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.Entities.ConventionEntity;
import com.example.rkwguapp.Entities.NotesEntity;

import java.sql.Timestamp;
import java.util.Date;

@Database(entities = {ConventionEntity.class, SubjectEntity.class, WorkshopEntity.class, NotesEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ConventionDao ConventionDao();
    public abstract SubjectDao SubjectDao();
    public abstract WorkshopDao WorkshopDao();
    public abstract NotesDao NotesDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ConventionDao ConventionDao;
        private SubjectDao SubjectDao;
        private WorkshopDao WorkshopDao;
        private NotesDao NotesDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            ConventionDao = db.ConventionDao();
            SubjectDao = db.SubjectDao();
            WorkshopDao = db.WorkshopDao();
            NotesDao = db.NotesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Date date1 = DateConverter.toDateType("06-01-2020");
            Date date2 = DateConverter.toDateType("01-01-2021");
            Date date3 = DateConverter.toDateType("07-20-2020");
            Date date4 = DateConverter.toDateType("09-05-2020");
            Date date5 = DateConverter.toDateType("08-30-2020");
            Date date6 = DateConverter.toDateType("10-15-2020");
            Date date7 = DateConverter.toDateType("12-31-2020");
            Date date8 = DateConverter.toDateType("06-01-2021");
            Date date9 = DateConverter.toDateType("10-05-2020");
            Date date10 = DateConverter.toDateType("09-30-2020");
            Date date11 = DateConverter.toDateType("09-01-2020");
            Date date12 = DateConverter.toDateType("10-16-2020");

            Date dateT = DateConverter.toTimeStamp("01-01-2020 11:11 AM");
            Date dateT3 = DateConverter.toTimeStamp("02-02-2020 22:22 PM");

            ConventionDao.insert(new ConventionEntity("Ethereum", date1, date7));
            ConventionDao.insert(new ConventionEntity("Ethical Hacking", date2, date8));

            SubjectDao.insert(new SubjectEntity("Contracts", "Ethereum", date3, date9));
            SubjectDao.insert(new SubjectEntity("Database", "Ethical Hacking", date4, date10));

            WorkshopDao.insert(new WorkshopEntity("DeFi", "Contracts", "S1", date5, date11, dateT, dateT3));
            WorkshopDao.insert(new WorkshopEntity("Hack", "Database", "C5", date6, date12, dateT, dateT3));

            NotesDao.insert(new NotesEntity("Note 1", "Here is my note 1", "DeFi"));
            NotesDao.insert(new NotesEntity("Note 2", "Here is my note 2", "Hack"));
            NotesDao.insert(new NotesEntity("Note 3", "Here is my note 3", "DeFi"));
            NotesDao.insert(new NotesEntity("Note 4", "Here is my note 4", "Hack"));
            return null;
        }
    }
}
