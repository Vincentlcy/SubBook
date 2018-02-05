/*
 * Copyright (c) 2018. Chuyang LIU all right Reserved.
 * You may used, distribute or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise connect chuyang1@ualberta.ca
 */

package com.example.suqing.chuyang1_subbook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.example.suqing.chuyang1_subbook.MainActivity.SEND_SUB;

/**
 * This class Add_sub represent the action adding new subscription
 * Cannot use intent to trans Sub Class
 *
 * Create by chuyang1 on 02/02/2018 (DD/MM)
 */

public class Add_sub extends AppCompatActivity {

    /**
     * most base method of the activity
     * @param savedInstanceState system control
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LifeCycle ---->", "Add_sub onCreate is called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
    }

    /**
     * The action of saving new subscription
     * @param view click action
     */

    public void save(View view) {
        Log.i("LifeCycle ---->", "Add_sub save is called");

        String error = "";
        boolean saving = true;

        EditText editTextName = (EditText) findViewById(R.id.editText_Name);
        String name = editTextName.getText().toString();


        EditText editTextYear = (EditText) findViewById(R.id.editTextYear);
        int year = 2000;
        Log.i("LifeCycle ---->", "2");
        if (!editTextYear.getText().toString().isEmpty()) {
            year = Integer.parseInt(editTextYear.getText().toString());
        } else {
            saving = false;
            error = new StringBuilder().append(error).append("\nYear cannot be empty.").toString();
        }

        EditText editTextMonth = (EditText) findViewById(R.id.editTextMonth);
        int month = 01;
        Log.i("LifeCycle ---->", "3");
        if (!editTextMonth.getText().toString().isEmpty()) {
            month = Integer.parseInt(editTextMonth.getText().toString());
        } else {
            saving = false;
            error = new StringBuilder().append(error).append("\nMonth cannot be empty.").toString();
        }

        EditText editTextDay = (EditText) findViewById(R.id.editTextDay);
        int day = 01;
        if (!editTextDay.getText().toString().isEmpty()) {
            day = Integer.parseInt(editTextDay.getText().toString());
        } else {
            saving = false;
            error = new StringBuilder().append(error).append("\nDay cannot be empty.").toString();
        }

        EditText editTextCharge = (EditText) findViewById(R.id.editTextCharge);
        double charge = 0.00;
        if (!editTextCharge.getText().toString().isEmpty()) {
            charge = Double.parseDouble(editTextCharge.getText().toString());
        } else {
            saving = false;
            error = new StringBuilder().append(error).append("\nCharge cannot be empty.").toString();
        }


        EditText editTextComment = (EditText) findViewById(R.id.editTextComment);
        String comment = editTextComment.getText().toString();




        Log.i("LifeCycle ---->", "Add_sub save check");

        if (name.length() < 1) {
            saving = false;
            error = new StringBuilder().append(error).append("\nName Should not be empty.")
                    .toString();
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
                error = new StringBuilder().append(error).append("\n" + month
                        + " no more than 30 days.").toString();
            }
        }

        if (saving) {

            Log.i("LifeCycle ---->", "return");
            Sub send = new Sub(name, year, month, day, charge, comment);
            ArrayList<Sub> subArrayList = new ArrayList<Sub>();
            Log.i("LifeCycle ---->", "Trick way");
            try {
                FileInputStream fis = openFileInput(MainActivity.FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                Gson gson = new Gson();

                //Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
                // 2018-01-23
                Type listType = new TypeToken<ArrayList<Sub>>(){}.getType();
                subArrayList = gson.fromJson(in, listType);
            } catch (FileNotFoundException e) {
                subArrayList = new ArrayList<Sub>();
            }

            subArrayList.add(send);

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
            finish();
            /*
            Intent intent;
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("1", send);
            setResult(1, intent);
            finish();
            Bundle extras = new Bundle();
            extras.putString("name", name);
            extras.putInt("year", year);
            extras.putString("Comment", comment);
            extras.putInt("month", month);
            extras.putInt("day", day);
            extras.putDouble("charge",charge);
            intent.putExtras(extras);
            setResult(1, intent);
            finish();
            */

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
}
