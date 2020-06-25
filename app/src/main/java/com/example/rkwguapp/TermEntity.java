package com.example.rkwguapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
public class TermEntity {

    @PrimaryKey
    private String termTitle;
    private Date termStartDate;
    private Date termEndDate;

    public TermEntity(String termTitle, Date termStartDate, Date termEndDate) {
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }
}
