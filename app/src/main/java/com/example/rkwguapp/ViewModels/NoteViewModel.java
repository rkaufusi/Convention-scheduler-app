package com.example.rkwguapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rkwguapp.Database.AppRepository;
import com.example.rkwguapp.Entities.NotesEntity;


import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private AppRepository repository;
    public static LiveData<List<NotesEntity>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(NotesEntity NotesEntity) { repository.insert(NotesEntity); }

    public void update(NotesEntity NotesEntity) { repository.update(NotesEntity); }

    public void delete(NotesEntity NotesEntity) { repository.delete(NotesEntity); }

    public static LiveData<List<NotesEntity>> getAllNotes() { return allNotes; }
}
