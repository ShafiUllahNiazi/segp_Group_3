package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddStudent extends AppCompatActivity {

    Toolbar mToolBar;
    Button saveButton;
    Button cancelBtn;
    EditText stdName;
    EditText stdUob;
    EditText stdYear;
    DbHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        mToolBar = (Toolbar) findViewById(R.id.add_student_app_bar);
        saveButton = (Button) findViewById(R.id.add_student_save_btn);
        stdName = (EditText) findViewById(R.id.new_student_edit_name);
        stdUob = (EditText) findViewById(R.id.new_student_uob_nbr);
        stdYear = (EditText)findViewById(R.id.new_student_year_nbr);
        cancelBtn = (Button) findViewById(R.id.add_student_cancel_btn);
        database = new DbHelper(this);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("New Student");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudent();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

    }

    private void insertStudent() {

        String uob = stdUob.getText().toString();
        String name = stdName.getText().toString();
        String year = stdYear.getText().toString();
        if(!uob.isEmpty() && !name.isEmpty() && !year.isEmpty()) {
           if(checkUob(uob)){
               database.insertStudent(uob, name, year, "0");
           }
           else{Toast.makeText(AddStudent.this, "UOB Already Exists", Toast.LENGTH_SHORT).show();}
        }
        else if(isValidName(stdName.toString())==false){
            Toast.makeText(this, "name is not valid", Toast.LENGTH_SHORT).show();
        }

        else{Toast.makeText(AddStudent.this, "Fill All The Fields", Toast.LENGTH_SHORT).show();}
        }
    private boolean checkUob(String uob) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM STUDENT_TABLE where uob='"+uob+"'", null);
        if(c.getCount() > 0)
        {
            Toast.makeText(AddStudent.this, "UOB Already Exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    public boolean isValidName(String name){
        Pattern fileExtnPtrn = Pattern.compile("/^[a-zA-Z ]*$/");
        Matcher mtch = fileExtnPtrn.matcher(name);
        if(mtch.matches()){
            return true;
        }
        return false;
    }


    }

