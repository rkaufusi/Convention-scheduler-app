package com.example.rkwguapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rkwguapp.DAO.WorkshopDao;
import com.example.rkwguapp.DAO.SubjectDao;
import com.example.rkwguapp.DAO.ConventionDao;
import com.example.rkwguapp.DAO.NotesDao;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.Entities.ConventionEntity;
import com.example.rkwguapp.Entities.NotesEntity;

import java.util.List;

public class AppRepository {
    private ConventionDao ConventionDao;
    private SubjectDao SubjectDao;
    private WorkshopDao WorkshopDao;
    private NotesDao NotesDao;
    private LiveData<List<ConventionEntity>> allConventions;
    private LiveData<List<SubjectEntity>> allSubjects;
    private LiveData<List<WorkshopEntity>> allWorkshops;
    private LiveData<List<NotesEntity>> allNotes;

    ConventionEntity ConventionEntity;
    private AppDatabase database;

    // test
    public AppRepository(Application application) {
        database = AppDatabase.getInstance(application);
        ConventionDao = database.ConventionDao();
        SubjectDao = database.SubjectDao();
        WorkshopDao = database.WorkshopDao();
        NotesDao = database.NotesDao();

        allConventions = ConventionDao.getAllConventions();
        allSubjects = SubjectDao.getAllSubjects();
        allWorkshops = WorkshopDao.getAllWorkshops();
        allNotes = NotesDao.getAllNotes();

    }
    // Convention
    public void insert(ConventionEntity ConventionEntity) {
        new InsertConventionAsyncTask(ConventionDao).execute(ConventionEntity);
    }
    public void update(ConventionEntity ConventionEntity) {
        new UpdateConventionAsyncTask(ConventionDao).execute(ConventionEntity);
    }
    public void delete(ConventionEntity ConventionEntity) {
        new DeleteConventionAsyncTask(ConventionDao).execute(ConventionEntity);
    }
    public LiveData<List<ConventionEntity>> getAllConventions() {
        return allConventions;
    }

    // Subject
    public void insert(SubjectEntity SubjectEntity) {
        new InsertSubjectAsyncTask(SubjectDao).execute(SubjectEntity);
    }
    public void update(SubjectEntity SubjectEntity) {
        new UpdateSubjectAsyncTask(SubjectDao).execute(SubjectEntity);
    }
    public void delete(SubjectEntity SubjectEntity) {
        new DeleteSubjectAsyncTask(SubjectDao).execute(SubjectEntity);
    }
    public LiveData<List<SubjectEntity>> getAllSubjects() {
        return allSubjects;
    }

    // Workshop
    public void insert(WorkshopEntity WorkshopEntity) {
        new InsertWorkshopAsyncTask(WorkshopDao).execute(WorkshopEntity);
    }
    public void update(WorkshopEntity WorkshopEntity) {
        new UpdateWorkshopAsyncTask(WorkshopDao).execute(WorkshopEntity);
    }
    public void delete(WorkshopEntity WorkshopEntity) {
        new DeleteWorkshopAsyncTask(WorkshopDao).execute(WorkshopEntity);
    }
    public LiveData<List<WorkshopEntity>> getAllWorkshops() {
        return allWorkshops;
    }

    // Notes
    public void insert(NotesEntity NotesEntity) {
        new InsertNoteAsyncTask(NotesDao).execute(NotesEntity);
    }
    public void update(NotesEntity NotesEntity) {
        new UpdateNoteAsyncTask(NotesDao).execute(NotesEntity);
    }
    public void delete(NotesEntity NotesEntity) {
        new DeleteNoteAsyncTask(NotesDao).execute(NotesEntity);
    }
    public LiveData<List<NotesEntity>> getAllNotes() {
        return allNotes;
    }

    // Convention AsyncTasks
    private static class InsertConventionAsyncTask extends AsyncTask<ConventionEntity, Void, Void> {
        private ConventionDao ConventionDao;

        private InsertConventionAsyncTask(ConventionDao ConventionDao) {
            this.ConventionDao = ConventionDao;
        }

        @Override
        protected Void doInBackground(ConventionEntity... conventionEntities) {
            ConventionDao.insert(conventionEntities[0]);
            return null;
        }
    }
    private static class UpdateConventionAsyncTask extends AsyncTask<ConventionEntity, Void, Void> {
        private ConventionDao ConventionDao;

        private UpdateConventionAsyncTask(ConventionDao ConventionDao) {
            this.ConventionDao = ConventionDao;
        }

        @Override
        protected Void doInBackground(ConventionEntity... conventionEntities) {
            ConventionDao.update(conventionEntities[0]);
            return null;
        }
    }
    private static class DeleteConventionAsyncTask extends AsyncTask<ConventionEntity, Void, Void> {
        private ConventionDao ConventionDao;

        private DeleteConventionAsyncTask(ConventionDao ConventionDao) {
            this.ConventionDao = ConventionDao;
        }

        @Override
        protected Void doInBackground(ConventionEntity... conventionEntities) {
            ConventionDao.delete(conventionEntities[0]);
            return null;
        }
    }

    // Subject AsyncTasks
    private static class InsertSubjectAsyncTask extends AsyncTask<SubjectEntity, Void, Void> {
        private SubjectDao SubjectDao;

        private InsertSubjectAsyncTask(SubjectDao SubjectDao) {
            this.SubjectDao = SubjectDao;
        }

        @Override
        protected Void doInBackground(SubjectEntity... subjectEntities) {
            SubjectDao.insert(subjectEntities[0]);
            return null;
        }
    }
    private static class UpdateSubjectAsyncTask extends AsyncTask<SubjectEntity, Void, Void> {
        private SubjectDao SubjectDao;

        private UpdateSubjectAsyncTask(SubjectDao SubjectDao) { this.SubjectDao = SubjectDao; }

        @Override
        protected Void doInBackground(SubjectEntity... subjectEntities) {
            SubjectDao.update(subjectEntities[0]);
            return null;
        }
    }
    private static class DeleteSubjectAsyncTask extends AsyncTask<SubjectEntity, Void, Void> {
        private SubjectDao SubjectDao;

        private DeleteSubjectAsyncTask(SubjectDao SubjectDao) {
            this.SubjectDao = SubjectDao;
        }

        @Override
        protected Void doInBackground(SubjectEntity... subjectEntities) {
            SubjectDao.delete(subjectEntities[0]);
            return null;
        }
    }

    // Workshop AsyncTasks
    private static class InsertWorkshopAsyncTask extends AsyncTask<WorkshopEntity, Void, Void> {
        private WorkshopDao WorkshopDao;

        private InsertWorkshopAsyncTask(WorkshopDao WorkshopDao) { this.WorkshopDao = WorkshopDao; }

        @Override
        protected Void doInBackground(WorkshopEntity... workshopEntities) {
            WorkshopDao.insert(workshopEntities[0]);
            return null;
        }
    }
    private static class UpdateWorkshopAsyncTask extends AsyncTask<WorkshopEntity, Void, Void> {
        private WorkshopDao WorkshopDao;

        private UpdateWorkshopAsyncTask(WorkshopDao WorkshopDao) { this.WorkshopDao = WorkshopDao; }

        @Override
        protected Void doInBackground(WorkshopEntity... workshopEntities) {
            WorkshopDao.update(workshopEntities[0]);
            return null;
        }
    }
    private static class DeleteWorkshopAsyncTask extends AsyncTask<WorkshopEntity, Void, Void> {
        private WorkshopDao WorkshopDao;

        private DeleteWorkshopAsyncTask(WorkshopDao WorkshopDao) { this.WorkshopDao = WorkshopDao; }

        @Override
        protected Void doInBackground(WorkshopEntity... workshopEntities) {
            WorkshopDao.delete(workshopEntities[0]);
            return null;
        }
    }


    // Note AsyncTasks
    private static class InsertNoteAsyncTask extends AsyncTask<NotesEntity, Void, Void> {
        private NotesDao NotesDao;

        private InsertNoteAsyncTask(NotesDao NotesDao) { this.NotesDao = NotesDao; }

        @Override
        protected Void doInBackground(NotesEntity... noteEntities) {
            NotesDao.insert(noteEntities[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<NotesEntity, Void, Void> {
        private NotesDao NotesDao;

        private UpdateNoteAsyncTask(NotesDao NotesDao) { this.NotesDao = NotesDao; }

        @Override
        protected Void doInBackground(NotesEntity... noteEntities) {
            NotesDao.update(noteEntities[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<NotesEntity, Void, Void> {
        private NotesDao NotesDao;

        private DeleteNoteAsyncTask(NotesDao NotesDao) { this.NotesDao = NotesDao; }

        @Override
        protected Void doInBackground(NotesEntity... noteEntities) {
            NotesDao.delete(noteEntities[0]);
            return null;
        }
    }
}
