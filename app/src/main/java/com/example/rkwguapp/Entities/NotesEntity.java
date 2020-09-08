package com.example.rkwguapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "notes_table")
public class NotesEntity {

        @PrimaryKey(autoGenerate = true)
        private int id;
        private String noteTitle;
        private String noteBody;
        private String noteWorkshop;

        public NotesEntity(String noteTitle, String noteBody, String noteWorkshop) {
            this.noteTitle = noteTitle;
            this.noteBody = noteBody;
            this.noteWorkshop = noteWorkshop;
        }

        public void setId(int id) { this.id = id; }

        public int getId() { return id; }

        public String getNoteTitle() { return noteTitle; }

        public String getNoteBody() { return noteBody; }

        public String getNoteWorkshop() { return noteWorkshop; }

}
