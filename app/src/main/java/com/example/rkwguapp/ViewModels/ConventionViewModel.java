package com.example.rkwguapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rkwguapp.Database.AppRepository;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.Entities.ConventionEntity;

import java.util.List;

public class ConventionViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<ConventionEntity>> allConventions;
    private  LiveData<List<SubjectEntity>> allConventionsCourses;

    public ConventionViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allConventions = repository.getAllConventions();
    }

    public void insert(ConventionEntity ConventionEntity) { repository.insert(ConventionEntity); }

    public void update(ConventionEntity ConventionEntity) {
        repository.update(ConventionEntity);
    }

    public void delete(ConventionEntity ConventionEntity) {
        repository.delete(ConventionEntity);
    }

    public LiveData<List<ConventionEntity>> getAllConventions() {
        return allConventions;
    }

}
