package com.example.rkwguapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;
    private LiveData<List<TermEntity>> allTerms;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<AssessmentEntity>> allAssessments;

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        termDao = database.termDao();
        courseDao = database.courseDao();
        assessmentDao = database.assessmentDao();

        allTerms = termDao.getAllTerms();
        allCourses = courseDao.getAllCourses();
        allAssessments = assessmentDao.getAllAssessments();
    }
}
