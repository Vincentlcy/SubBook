/*
 * Copyright (c) 2018. Chuyang LIU all right Reserved.
 * You may used, distribute or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise connect chuyang1@ualberta.ca
 */

package com.example.suqing.chuyang1_subbook;

import java.io.Serializable;
import java.util.Date;

/**
 * represent the sub information
 */

public class Sub implements Serializable{
    private String name;
    private Date date;
    private int year;
    private int month;
    private int day;
    private double charge;
    private String comment;

    /**
     * defult setting
     * @param name string name of the subscription
     * @param year int year no limit
     * @param month int month has limit
     * @param day int day has limit
     * @param charge double has limit
     * @param comment string
     */

    public Sub(String name, int year, int month, int day, double charge, String comment) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.charge = charge;
        this.comment = comment;
    }

    /**
     * return name
     * @return string name
     */

    public String getName() {return name;}

    /**
     * get comment
     * @return string comment
     */
    public String getComment() {return comment;}

    /**
     * get year
     * @return int year
     */
    public int getYear() {return year;}

    /**
     * get month
     * @return int month
     */
    public int getMonth() {return month;}

    /**
     * get day
     * @return int day
     */
    public int getDay() {return day;}

    /**
     * return charge
     * @return double charge
     */
    public double getCharge() {return charge;}


    /**
     * reset of the sub
     * @param name string name of the subscription
     * @param year int year no limit
     * @param month int month has limit
     * @param day int day has limit
     * @param charge double has limit
     * @param comment string
     */
    public void changeSub(String name, int year, int month, int day, double charge, String comment) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.charge = charge;
        this.comment = comment;
    }
}
