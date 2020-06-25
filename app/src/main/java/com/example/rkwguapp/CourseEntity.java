package com.example.rkwguapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "course_table")
public class CourseEntity {

    @PrimaryKey
    private String courseTitle;
    private String associatedTerm;
    private Date courseStartDate;
    private Date courseEndDate;
    private String courseStatus;
    private String courseMentor;
    private String courseMentorPhone;
    private String courseMentorEmail;
    private String courseNote;

    public CourseEntity(String courseTitle, String associatedTerm, Date courseStartDate, Date courseEndDate, String courseStatus, String courseMentor, String courseMentorPhone, String courseMentorEmail, String courseNote) {
        this.courseTitle = courseTitle;
        // not sure if i need this
        this.associatedTerm = associatedTerm;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentor = courseMentor;
        this.courseMentorPhone = courseMentorPhone;
        this.courseMentorEmail = courseMentorEmail;
        this.courseNote = courseNote;

    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getAssociatedTerm() {
        return associatedTerm;
    }

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
}