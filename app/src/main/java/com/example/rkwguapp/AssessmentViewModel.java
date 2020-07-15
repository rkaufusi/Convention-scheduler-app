package com.example.rkwguapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<AssessmentEntity>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allAssessments = repository.getAllAssessments();
    }

    public void insert(AssessmentEntity assessmentEntity) {
        repository.insert(assessmentEntity);
    }

    public void update(AssessmentEntity assessmentEntity) {
        repository.update(assessmentEntity);
    }

    public void delete(AssessmentEntity assessmentEntity) {
        repository.delete(assessmentEntity);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return allAssessments;
    }
}
