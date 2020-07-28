package com.example.rkwguapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;
    private LiveData<List<TermEntity>> allTerms;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<AssessmentEntity>> allAssessments;

    //private LiveData<List<CourseEntity>> allTermCourses;
    TermEntity termEntity;
    private AppDatabase database;

    // test


    public AppRepository(Application application) {
        database = AppDatabase.getInstance(application);
        termDao = database.termDao();
        courseDao = database.courseDao();
        assessmentDao = database.assessmentDao();


        allTerms = termDao.getAllTerms();
        allCourses = courseDao.getAllCourses();
        allAssessments = assessmentDao.getAllAssessments();

        //allTermCourses = courseDao.getCoursesByTerm(termEntity.getTermTitle());


    }
    // Term
    public void insert(TermEntity termEntity) {
        new InsertTermAsyncTask(termDao).execute(termEntity);
    }
    public void update(TermEntity termEntity) {
        new UpdateTermAsyncTask(termDao).execute(termEntity);
    }
    public void delete(TermEntity termEntity) {
        new DeleteTermAsyncTask(termDao).execute(termEntity);
    }
    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    // trial
    /*
    public List<CourseEntity> getTermCourses(int termId){
        return database.courseDao().getTermCourses(termId);
    } */

    // Course
    public void insert(CourseEntity courseEntity) {
        new InsertCourseAsyncTask(courseDao).execute(courseEntity);
    }
    public void update(CourseEntity courseEntity) {
        new UpdateCourseAsyncTask(courseDao).execute(courseEntity);
    }
    public void delete(CourseEntity courseEntity) {
        new DeleteCourseAsyncTask(courseDao).execute(courseEntity);
    }
    public LiveData<List<CourseEntity>> getAllCourses() {
        return allCourses;
    }

    // test
    //public LiveData<List<CourseEntity>> getAllTermCourses() { return allTermCourses; }


    // Assessment
    public void insert(AssessmentEntity assessmentEntity) {
        new InsertAssessmentAsyncTask(assessmentDao).execute(assessmentEntity);
    }
    public void update(AssessmentEntity assessmentEntity) {
        new UpdateAssessmentAsyncTask(assessmentDao).execute(assessmentEntity);
    }
    public void delete(AssessmentEntity assessmentEntity) {
        new DeleteAssessmentAsyncTask(assessmentDao).execute(assessmentEntity);
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return allAssessments;
    }

    // Term AsyncTasks
    private static class InsertTermAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDao termDao;

        private InsertTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(TermEntity... termEntities) {
            termDao.insert(termEntities[0]);
            return null;
        }
    }
    private static class UpdateTermAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDao termDao;

        private UpdateTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(TermEntity... termEntities) {
            termDao.update(termEntities[0]);
            return null;
        }
    }
    private static class DeleteTermAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDao termDao;

        private DeleteTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(TermEntity... termEntities) {
            termDao.delete(termEntities[0]);
            return null;
        }
    }

    // Course AsyncTasks
    private static class InsertCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(CourseEntity... courseEntities) {
            courseDao.insert(courseEntities[0]);
            return null;
        }
    }
    private static class UpdateCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) { this.courseDao = courseDao; }

        @Override
        protected Void doInBackground(CourseEntity... courseEntities) {
            courseDao.update(courseEntities[0]);
            return null;
        }
    }
    private static class DeleteCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(CourseEntity... courseEntities) {
            courseDao.delete(courseEntities[0]);
            return null;
        }
    }

    // Assessment AsyncTasks
    private static class InsertAssessmentAsyncTask extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDao assessmentDao;

        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao) { this.assessmentDao = assessmentDao; }

        @Override
        protected Void doInBackground(AssessmentEntity... assessmentEntities) {
            assessmentDao.insert(assessmentEntities[0]);
            return null;
        }
    }
    private static class UpdateAssessmentAsyncTask extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDao assessmentDao;

        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao) { this.assessmentDao = assessmentDao; }

        @Override
        protected Void doInBackground(AssessmentEntity... assessmentEntities) {
            assessmentDao.update(assessmentEntities[0]);
            return null;
        }
    }
    private static class DeleteAssessmentAsyncTask extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDao assessmentDao;

        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao) { this.assessmentDao = assessmentDao; }

        @Override
        protected Void doInBackground(AssessmentEntity... assessmentEntities) {
            assessmentDao.delete(assessmentEntities[0]);
            return null;
        }
    }

}
