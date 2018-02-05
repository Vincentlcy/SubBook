/*
 * Copyright (c) 2018. Chuyang LIU all right Reserved.
 * You may used, distribute or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise connect chuyang1@ualberta.ca
 */

package com.example.suqing.chuyang1_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * represent the show subscription detail
 * Created by chuyang1 on 03/02/2018 (DD/MM)
 *
 */

public class ShowSub extends AppCompatActivity {

    protected ArrayList<Sub> subArrayList = new ArrayList<Sub>();
    public Sub sub;

    /**
     * The base of the activity
     * @param savedInstanceState system control
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sub);

        Log.i("LifeCycle ---->", "ShowSub is called");

        Intent intent = getIntent();
        int i = intent.getIntExtra("1", 1);

        loadFromFile();

        if (i >= 0) {
            setTextview(i);
        } else{

        }



    }

    /**
     * the action of click on save edit button
     * @param view view click
     */

    public void save(View view) {
        Log.i("LifeCycle ---->", "Add_sub save is called");

        String error = "";

        EditText editTextName = (EditText) findViewById(R.id.editText_Name);
        String name = editTextName.getText().toString();

        EditText editTextYear = (EditText) findViewById(R.id.editTextYear);
        int year = 2000;
        if (!editTextYear.getText().toString().isEmpty()) {
            year = Integer.parseInt(editTextYear.getText().toString());
        } else {
            error = new StringBuilder().append(error).append("\nYear cannot be empty.").toString();
        }

        EditText editTextMonth = (EditText) findViewById(R.id.editTextMonth);
        int month = 01;
        if (!editTextMonth.getText().toString().isEmpty()) {
            month = Integer.parseInt(editTextMonth.getText().toString());
        } else {
            error = new StringBuilder().append(error).append("\nMonth cannot be empty.").toString();
        }

        EditText editTextDay = (EditText) findViewById(R.id.editTextDay);
        int day = 01;
        if (!editTextDay.getText().toString().isEmpty()) {
            day = Integer.parseInt(editTextDay.getText().toString());
        } else {
            error = new StringBuilder().append(error).append("\nDay cannot be empty.").toString();
        }

        EditText editTextCharge = (EditText) findViewById(R.id.editTextCharge);
        double charge = 0.00;
        if (!editTextCharge.getText().toString().isEmpty()) {
            charge = Double.parseDouble(editTextCharge.getText().toString());
        } else {
            error = new StringBuilder().append(error).append("\nCharge cannot be empty.").toString();
        }

        EditText editTextComment = (EditText) findViewById(R.id.editTextComment);
        String comment = editTextComment.getText().toString();

        Log.i("LifeCycle ---->", "2");

        boolean saving = true;
        Log.i("LifeCycle ---->", "Add_sub save check");

        if (name.length() < 1) {
            saving = false;
            error = new StringBuilder().append(error).append("\nName Should not be empty.").
                    toString();
        }

        if (name.length() > 20) {
            saving = false;
            error = new StringBuilder().append(error).append("\nName no more than 20 bytes.")
                    .toString();
        }

        if (month >= 13 || month <= 0) {
            saving = false;
            error = new StringBuilder().append(error).append("\nMonth illegal.").toString();
        }

        if (day >= 32 || day <= 0) {
            saving = false;
            error = new StringBuilder().append(error).append("\nDay illegal.").toString();
        }

        if (charge < 0) {
            saving = false;
            error = new StringBuilder().append(error).append("\nCharge should be positive.")
                    .toString();
        }

        if (comment.length() > 30) {
            saving = false;
            error = new StringBuilder().append(error).append("\nComment no more than 30 bytes.")
                    .toString();
        }

        if (month == 2 && day > 28) {
            saving = false;
            error = new StringBuilder().append(error).append("\nFeb no more than 28 days.")
                    .toString();
        }

        if (month == 4||month == 5||month == 9||month == 11) {
            if (day > 30) {
                saving = false;
                error = new StringBuilder().append(error).append("\n" + month +
                        " no more than 30 days.").toString();
            }
        }

        if (saving) {
            Log.i("LifeCycle ---->", "save sub");
            sub.changeSub(name, year, month, day, charge, comment);
            saveInFile();
            finish();
        } else {
            Log.i("LifeCycle ---->", "Error");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage(error);
            builder.setCancelable(true);
            builder.create().show();
            onStart();
        }
    }

    /**
     * set the text in edittext at the start of the function
     * @param i int represent the index of the sub in subArrayList
     */

    private void setTextview(int i) {
        Log.i("LifeCycle ---->", "setText is called");
        sub = subArrayList.get(i);
        String name = sub.getName();
        int year = sub.getYear();
        int month = sub.getMonth();
        int day = sub.getDay();
        double charge = sub.getCharge();
        String comment = sub.getComment();

        Log.i("LifeCycle ---->", "1");

        EditText editTextName = (EditText) findViewById(R.id.editText_Name);
        editTextName.setText(name, TextView.BufferType.EDITABLE);
        // editTextName.setFocusable(false);
        // editTextName.setFocusableInTouchMode(false);

        Log.i("LifeCycle ---->", "n");

        EditText editTextYear = (EditText) findViewById(R.id.editTextYear);
        editTextYear.setText(""+year);
        // editTextYear.setFocusable(false);
        // editTextYear.setFocusableInTouchMode(false);

        Log.i("LifeCycle ---->", "y");

        EditText editTextMonth = (EditText) findViewById(R.id.editTextMonth);
        editTextMonth.setText(""+month);
        // editTextMonth.setFocusable(false);
        // editTextMonth.setFocusableInTouchMode(false);

        Log.i("LifeCycle ---->", "setText is m");

        EditText editTextDay = (EditText) findViewById(R.id.editTextDay);
        editTextDay.setText(""+day);
        // editTextDay.setFocusable(false);
        // editTextDay.setFocusableInTouchMode(false);

        Log.i("LifeCycle ---->", "setText is d");

        EditText editTextCharge = (EditText) findViewById(R.id.editTextCharge);
        editTextCharge.setText(String.valueOf(charge));
        // editTextCharge.setFocusable(false);
        // editTextCharge.setFocusableInTouchMode(false);

        Log.i("LifeCycle ---->", "setText is ch");

        EditText editTextComment = (EditText) findViewById(R.id.editTextComment);
        editTextComment.setText(comment, TextView.BufferType.EDITABLE);
        // editTextComment.setFocusable(false);
        // editTextComment.setFocusableInTouchMode(false);

        Log.i("LifeCycle ---->", "setText is co");

    }

    /**
     * load function of the app in ShowSub class
     */
    protected void loadFromFile() {
        Log.i("LifeCycle ---->", "load file is called");
        try {
            FileInputStream fis = openFileInput(MainActivity.FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-
            // typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<ArrayList<Sub>>(){}.getType();
            subArrayList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            subArrayList = new ArrayList<Sub>();
        }
    }

    /**
     * save the file to avoid data lost
     *
     */

    protected void saveInFile() {
        Log.i("LifeCycle ---->", "save file is called");
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subArrayList, out);
            out.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.i("LifeCycle ---->", "save error1 is called");
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i("LifeCycle ---->", "save error2 is called");
            throw new RuntimeException();
        }
    }
}
