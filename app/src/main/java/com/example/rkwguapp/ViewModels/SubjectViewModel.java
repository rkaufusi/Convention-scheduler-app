package com.example.rkwguapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rkwguapp.Database.AppRepository;
import com.example.rkwguapp.Entities.SubjectEntity;

import java.util.List;

public class SubjectViewModel extends AndroidViewModel {
    private AppRepository repository;
    public static LiveData<List<SubjectEntity>> allSubjects;

    public SubjectViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allSubjects = repository.getAllSubjects();
    }

    public void insert(SubjectEntity SubjectEntity) { repository.insert(SubjectEntity); }

    public void update(SubjectEntity SubjectEntity) { repository.update(SubjectEntity); }

    public void delete(SubjectEntity SubjectEntity) { repository.delete(SubjectEntity); }

    public static LiveData<List<SubjectEntity>> getAllSubjects() { return allSubjects; }
}