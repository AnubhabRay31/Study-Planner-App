package com.example.outlab9;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.outlab9.MyCoreDatabase;
import com.example.outlab9.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class AddActivityA extends AppCompatActivity {

    int type = 2;//hard-coding here
    EditText title, course, description, myTime, myDate;
    //    LocalDate myDate;
    //    LocalTime myTime;
    MyCoreDatabase myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a);


        title = (EditText) findViewById(R.id.title_text);
        course = (EditText) findViewById(R.id.course_text);
        description = (EditText) findViewById(R.id.description_text);
        myDate = (EditText) findViewById(R.id.date_text);
        myTime = (EditText) findViewById(R.id.time_text);

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF018786")));
        }

        myDataset = new MyCoreDatabase(this);
    }

    public void doSave(View view) {
        DateTimeFormatter f1 = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-d"))
                .toFormatter();

        DateTimeFormatter f2 = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("HH:mm"))
                .toFormatter();

        LocalDate localDate = null;
        LocalTime localTime = null;
        if(myDate.getText().toString().length() !=0 && myTime.getText().toString().length() !=0) {
            localDate = LocalDate.parse(myDate.getText().toString(), f1);
            localTime = LocalTime.parse(myTime.getText().toString(), f2);
        }else {
            Toast.makeText(getApplicationContext(),"Date and time not specified properly",Toast.LENGTH_SHORT).show();
            Log.i("Database", "doSave: Date , Time not properly specified. ");
        }

        boolean status = myDataset.insertData(type,
                title.getText().toString(),
                course.getText().toString(),
                description.getText().toString(),
                localDate,
                localTime
        );

        if(status) {
            //now make all fields empty again
            title.setText("");
            course.setText("");
            description.setText("");
            myDate.setText("");
            myTime.setText("");
        }


    }
    public void doLoad(View view) {
        myDataset.getAll();
    }
}