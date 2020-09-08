package com.example.rkwguapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Date;

@Entity(tableName = "workshop_table")
public class WorkshopEntity {

    @PrimaryKey(autoGenerate = true)
    private int workshopId;
    private String workshopTitle;
    private String associatedSubject;
    private String room;
    private Date workshopDate;
    private Date startTime;
    private Date workshopCompleteDate;
    private Date endTime;

    public WorkshopEntity(String workshopTitle, String associatedSubject, String room, Date workshopDate, Date workshopCompleteDate, Date startTime, Date endTime) {
        this.workshopTitle = workshopTitle;
        this.associatedSubject = associatedSubject;
        this.room = room;
        this.workshopDate = workshopDate;
        this.workshopCompleteDate = workshopCompleteDate;
        this.startTime = startTime;
        this.endTime = endTime;

    }
    // assessment Id

    public void setWorkshopId(int workshopId) { this.workshopId = workshopId; }

    public int getWorkshopId() { return workshopId; }

    public String getAssociatedSubject() { return associatedSubject; }

    public String getWorkshopTitle() { return workshopTitle; }

    public String getRoom() { return room; }

    public Date getWorkshopDate() { return workshopDate; }

    public Date getStartTime() { return startTime; }

    public Date getWorkshopCompleteDate() { return workshopCompleteDate; }

    public Date getEndTime() { return endTime; }

}
