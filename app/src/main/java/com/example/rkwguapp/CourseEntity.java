package com.example.rkwguapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

@Entity(tableName = "course_table")
public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseTitle;
    private String associatedTerm;
    private Date courseStartDate;
    private Date courseEndDate;
    private String courseStatus;
    private String courseMentor;
    private String courseMentorPhone;
    private String courseMentorEmail;
    private String courseNote;
    private int termId;


    public CourseEntity(String courseTitle, String associatedTerm, Date courseStartDate, Date courseEndDate, String courseStatus, String courseMentor, String courseMentorPhone, String courseMentorEmail, String courseNote) {
        this.courseTitle = courseTitle;
        this.associatedTerm = associatedTerm;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentor = courseMentor;
        this.courseMentorPhone = courseMentorPhone;
        this.courseMentorEmail = courseMentorEmail;
        this.courseNote = courseNote;
        this.termId = termId;

    }

    // courseId

    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getCourseId() { return courseId; }

    //getters

    public String getCourseTitle() { return courseTitle; }

    public String getAssociatedTerm() { return associatedTerm; }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public String getCourseMentor() {
        return courseMentor;
    }

    public String getCourseMentorPhone() {
        return courseMentorPhone;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public String getCourseNote() {
        return courseNote;
    }

    public int getTermId(){ return termId; }

    public void setTermId(int termId){ this.termId = termId; }

}
