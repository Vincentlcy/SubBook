/*
 * Copyright (c) 2018. Chuyang LIU all right Reserved.
 * You may used, distribute or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise connect chuyang1@ualberta.ca
 */

package com.example.suqing.chuyang1_subbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * This class represent the main activity which is the main page
 * Created by chuyang1 on 02/02/2018
 */

public class MainActivity extends AppCompatActivity {

    protected static final String FILENAME = "sublist.sac";
    protected static final String SEND_SUB = "com.example.suqing.chuyang1_subbook.sub";
    private ListView subList;

    protected ArrayList<Sub> subArrayList = new ArrayList<Sub>();
    private ArrayAdapter<String> subArrayAdapter;

    /**
     * The base of the activity
     * @param savedInstanceState system control
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LifeCycle ---->", "onCreate is called");
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subList = (ListView) findViewById(R.id.SubList);

        subList.setClickable(true);
        subList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveInFile();
                Log.i("LifeCycle ---->", "ShortClick");

                Intent intent = new Intent(MainActivity.this, ShowSub.class);
                intent.putExtra("1", (int) i);
                startActivity(intent);
                saveInFile();
            }
        });

        subList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("LifeCycle ---->", "LongClick");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Record");
                builder.setMessage("Do you want to delete this record?");
                builder.setCancelable(true);
                final int posi = i;

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("LifeCycle ---->", ""+posi);
                        subArrayList.remove(posi);
                        Log.i("LifeCycle ---->", "DeleteSuccess");
                        saveInFile();
                        onStart();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("LifeCycle ---->", "Cancel");
                    }
                });
                builder.create().show();
                onStart();

                return true;
            }
        });
    }

    /**
     * The base function of activity run after on_create()
     */

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.i("LifeCycle ---->", "onStart is called");

        loadFromFile();
        Log.i("LifeCycle ---->", "load file ended.");

        ArrayList<String> name = new ArrayList<String>();

        if (subArrayList == null) {
            subArrayList = new ArrayList<Sub>();
        }

        for (int i = 0; i < subArrayList.size(); i++) {
            String temp = subArrayList.get(i).getName() + "      " + subArrayList.get(i).getYear()+"-"
                    + subArrayList.get(i).getMonth() + "-" + subArrayList.get(i).getDay() + "   " +
                    subArrayList.get(i).getCharge();
            name.add(temp);
        }

        subArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.list_item,R.id.textView, name);

        subList.setAdapter(subArrayAdapter);

        show_sum();
    }

    /**
     * Control the show sum part of the main page
     */

    protected void show_sum() {
        double sum = 0;

        for (int i = 0; i < subArrayList.size(); i++) {
            sum += subArrayList.get(i).getCharge();
        }

        TextView sumTextView = (TextView) findViewById(R.id.textViewSum);
        sumTextView.setText("Sum: " + String.valueOf(sum));
    }

    /**
     * load function of the app in main class
     */

    protected void loadFromFile() {
        Log.i("LifeCycle ---->", "load file is called");
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
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
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
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

    /**
     * The action of click on main menu
     * @param menu
     * @return bool true
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * the action of click on add new button
     * @param view view click
     */

    public void add_new(View view) {
        Log.i("LifeCycle ---->", "add new is called");
        Intent intent = new Intent(this, Add_sub.class);
        startActivityForResult(intent, 1);;
    }


    /**
     * the action of get result of add_new
     * @param requestCode int 1
     * @param resultCode int should be 1
     * @param data intent data tran file
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("LifeCycle ---->", "return to main");
        loadFromFile();
        if (requestCode == 1) {
            if(getIntent().getSerializableExtra("1") != null){
                Log.i("LifeCycle ---->", "return not null");
                Sub temp = (Sub) getIntent().getSerializableExtra("1");
                System.out.print(temp.getName());

                // subArrayList.add(temp);
                saveInFile();
                onStart();
            }
        }
    }
}
