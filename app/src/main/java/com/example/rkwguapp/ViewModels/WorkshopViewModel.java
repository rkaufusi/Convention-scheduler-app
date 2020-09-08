package com.example.rkwguapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rkwguapp.Database.AppRepository;
import com.example.rkwguapp.Entities.WorkshopEntity;

import java.util.List;

public class WorkshopViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<WorkshopEntity>> allWorkshops;

    public WorkshopViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allWorkshops = repository.getAllWorkshops();
    }

    public void insert(WorkshopEntity WorkshopEntity) {
        repository.insert(WorkshopEntity);
    }

    public void update(WorkshopEntity WorkshopEntity) {
        repository.update(WorkshopEntity);
    }

    public void delete(WorkshopEntity WorkshopEntity) {
        repository.delete(WorkshopEntity);
    }

    public LiveData<List<WorkshopEntity>> getAllWorkshops() {
        return allWorkshops;
    }
}
