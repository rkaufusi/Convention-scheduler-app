package com.example.rkwguapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<TermEntity>> allTerms;
    private  LiveData<List<CourseEntity>> allTermsCourses;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insert(TermEntity termEntity) { repository.insert(termEntity); }

    public void update(TermEntity termEntity) {
        repository.update(termEntity);
    }

    public void delete(TermEntity termEntity) {
        repository.delete(termEntity);
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    public LiveData<List<CourseEntity>> getAllTermsCourses() {return allTermsCourses; }
}
