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

public class Add_sub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LifeCycle ---->", "Add_sub onCreate is called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
    }

    public void save(View view) {
        Log.i("LifeCycle ---->", "Add_sub save is called");

        String error = "";

        EditText editTextName = (EditText) findViewById(R.id.editText_Name);
        String name = editTextName.getText().toString();


        EditText editTextYear = (EditText) findViewById(R.id.editTextYear);
        int year = Integer.parseInt(editTextYear.getText().toString());

        EditText editTextMonth = (EditText) findViewById(R.id.editTextMonth);
        int month = Integer.parseInt(editTextMonth.getText().toString());

        EditText editTextDay = (EditText) findViewById(R.id.editTextDay);
        int day = Integer.parseInt(editTextDay.getText().toString());

        EditText editTextCharge = (EditText) findViewById(R.id.editTextCharge);
        double charge = Double.parseDouble(editTextCharge.getText().toString());

        EditText editTextComment = (EditText) findViewById(R.id.editTextComment);
        String comment = editTextComment.getText().toString();

        Log.i("LifeCycle ---->", "2");

        boolean saving = true;
        Log.i("LifeCycle ---->", "Add_sub save check");

        if (name.length() < 1) {
            saving = false;
            error = new StringBuilder().append(error).append("\nName Should not be empty.").toString();
        }

        if (name.length() > 20) {
            saving = false;
            error = new StringBuilder().append(error).append("\nName no more than 20 bytes.").toString();
        }

        if (month >= 13) {
            saving = false;
            error = new StringBuilder().append(error).append("\nMonth no more than 12.").toString();
        }

        if (day >= 32) {
            saving = false;
            error = new StringBuilder().append(error).append("\nDay no more than 31.").toString();
        }

        if (charge < 0) {
            saving = false;
            error = new StringBuilder().append(error).append("\nCharge should be positive.").toString();
        }

        if (comment.length() > 30) {
            saving = false;
            error = new StringBuilder().append(error).append("\nComment no more than 30 bytes.").toString();
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
