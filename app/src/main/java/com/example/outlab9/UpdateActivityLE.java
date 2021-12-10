package com.example.outlab9;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import androidx.appcompat.app.AlertDialog;

public class UpdateActivityLE extends AppCompatActivity {

    EditText title_input, course_input, description_input, date_input, time_input;
    Button button_update, button_delete;
    String id, title, course, description, date, time;
    int type=1;
    LocalDate localDate = null;
    LocalTime localTime = null;
    DateTimeFormatter f1 = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ofPattern("yyyy-MM-d"))
            .toFormatter();
    DateTimeFormatter f2 = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ofPattern("HH:mm"))
            .toFormatter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_le);

        title_input = findViewById(R.id.title_text_2);
        course_input = findViewById(R.id.course_text_2);
        description_input = findViewById(R.id.description_text_2);
        date_input = findViewById(R.id.date_text_2);
        time_input = findViewById(R.id.time_text_2);
        button_update = findViewById(R.id.button_update);
        button_delete = findViewById(R.id.button_delete);


        //first we call this for displaying the filled in data
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(title);
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF018786")));
        }

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCoreDatabase myDB = new MyCoreDatabase(UpdateActivityLE.this);
                //only then we call these
                title = title_input.getText().toString().trim();
                course = course_input.getText().toString().trim();
                description = description_input.getText().toString().trim();

                if(date_input.getText().toString().length() !=0 && time_input.getText().toString().length() !=0) {
                    localDate = LocalDate.parse(date_input.getText().toString(), f1);
                    localTime = LocalTime.parse(time_input.getText().toString(), f2);
                    Log.i("Database", "onClick: Date time is perfect in format");

                    Log.i("Database", "onClick: update data is ok\n" +
                            id + "," + type + "," + title + "," + course + "," + description + "," +
                            localDate.toString() + "," + localTime.toString()+".");

                    myDB.updateData(id,type,title,course,description,localDate,localTime);

                }else {
                    Toast.makeText(getApplicationContext(),"Date n time not specified properly",Toast.LENGTH_SHORT).show();
                    Log.i("Database", "onClick: Date , Time not properly specified. ");
                }
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }



    //for displaying the filled update-form for the user to make changes
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")
                && getIntent().hasExtra("course") && getIntent().hasExtra("description")
                && getIntent().hasExtra("date") && getIntent().hasExtra("time") ){

            //getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            course = getIntent().getStringExtra("course");
            description = getIntent().getStringExtra("description");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");

            //setting Intent data
            title_input.setText(title);
            course_input.setText(course);
            description_input.setText(description);
            date_input.setText(date);
            time_input.setText(time);


        }else {
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete "+ title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyCoreDatabase myDB = new MyCoreDatabase(UpdateActivityLE.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}