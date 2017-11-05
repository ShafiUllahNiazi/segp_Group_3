package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTeacher extends AppCompatActivity {

    private Toolbar mToolBar;
    private EditText mTeacherName;
    private EditText mTeacherEmail;
    private Button mSaveTeacherBtn;
    private  DbHelper database;
private Button cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        mTeacherName = (EditText) findViewById(R.id.new_teacher_edit_name);
        mTeacherEmail = (EditText) findViewById(R.id.new_teacher_email_id);
        database = new DbHelper(this);
        mSaveTeacherBtn = (Button) findViewById(R.id.add_teacher_save_btn);
        mToolBar = (Toolbar) findViewById(R.id.add_teacher_app_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Add Teacher");

        mSaveTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeacher();
            }
        });
        cancelBtn = (Button) findViewById(R.id.add_teacher_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allStudentIntent = new Intent(AddTeacher.this,AllTeachers.class);
                allStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(allStudentIntent);
            }
        });

    }

    private void saveTeacher() {

        String teacherEmail = mTeacherEmail.getText().toString();
        String teacherName = mTeacherName.getText().toString();

        if(!teacherEmail.isEmpty() && !teacherName.isEmpty() ){
            if(checkEmail(teacherEmail)){
                database.insertTeacher(teacherName,teacherEmail,"0");
            }
        }
        else if(validName(teacherName)==false){
            Toast.makeText(AddTeacher.this, "Incorrect Name", Toast.LENGTH_SHORT).show();
        }
        else if(isValidEmail(teacherEmail)==false){
            Toast.makeText(AddTeacher.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
        }


        else{
            Toast.makeText(AddTeacher.this, "Fill All The Fields", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean checkEmail(String email) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM TEACHER_TABLE where EMAIL='"+email+"'", null);
        if(c.getCount() > 0)
        {
            Toast.makeText(AddTeacher.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public boolean validName(String name){
        Pattern fileExtnPtrn = Pattern.compile("/^[a-zA-Z ]*$/");
        Matcher mtch = fileExtnPtrn.matcher(name);
        if(mtch.matches()){
            return true;
        }
        return false;
    }
}
