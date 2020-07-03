package com.example.rkwguapp;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static Date toDateType(String dateString){

        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
