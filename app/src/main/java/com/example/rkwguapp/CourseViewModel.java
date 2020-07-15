package com.example.rkwguapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<CourseEntity>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allCourses = repository.getAllCourses();
    }

    public void insert(CourseEntity courseEntity) { repository.insert(courseEntity); }

    public void update(CourseEntity courseEntity) { repository.update(courseEntity); }

    public void delete(CourseEntity courseEntity) { repository.delete(courseEntity); }

    public LiveData<List<CourseEntity>> getAllCourses() { return allCourses; }
}