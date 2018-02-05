package com.example.suqing.chuyang1_subbook;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by suqing on 03/02/18.
 */

public class Sub implements Serializable{
    private String name;
    private Date date;
    private int year;
    private int month;
    private int day;
    private double charge;
    private String comment;

    public Sub(String name, int year, int month, int day, double charge, String comment) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.charge = charge;
        this.comment = comment;
    }

    public String getName() {return name;}

    public String getComment() {return comment;}

    public int getYear() {return year;}

    public int getMonth() {return month;}

    public int getDay() {return day;}

    public double getCharge() {return charge;}


    public void changeSub(String name, int year, int month, int day, double charge, String comment) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.charge = charge;
        this.comment = comment;
    }
}
