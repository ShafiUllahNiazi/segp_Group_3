package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeacherProfile extends AppCompatActivity {

    String mTeacherName;
    TextView mTeacherEmail;
    TextView mTeacherGroup;
    String mTcherId;
    String mEmail;
    String mGroup;
    DbHelper mDatabase;
    private ArrayList<String> mTeacher;
    private String mTiD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.teacher_profile_toolbar);
        mTeacherEmail = (TextView) findViewById(R.id.teacher_profile_email_id);
        mTeacherGroup = (TextView) findViewById(R.id.teacher_profile_group_name);

        getAllIntent();
        mTeacher = new ArrayList<>();
        Toast.makeText(this,mTcherId, Toast.LENGTH_SHORT).show();
        mDatabase = new DbHelper(this);
        displayTeacher();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mTeacherName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.teacher_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editTeacherIntent = new Intent(TeacherProfile.this,EditTeacher.class);
                editTeacherIntent.putStringArrayListExtra("teacher",mTeacher);
                editTeacherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(editTeacherIntent);
            }
        });


    }
    public void getAllIntent(){
            mTcherId = getIntent().getStringExtra("Teacher_id");
    }

    private void displayTeacher() {

        ArrayList<Teacher> teacher = mDatabase.getSingleTeacher(mTcherId);
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM GROUP_NAM where G_ID = '"+teacher.get(0).getmGroupName()+"'", null);
        String name="No group";
        while (c.moveToNext()){
           name = c.getString(1);

        }

        mTeacherName =  teacher.get(0).getmTeacherName();
        mGroup  = teacher.get(0).getmGroupName();
        mEmail = teacher.get(0).getmTeacherEmail();
        mTeacherEmail.setText(mEmail);
        mTeacherGroup.setText(name);
        mTeacher.add(mTcherId);
        mTeacher.add(mTeacherName);
        mTeacher.add(mEmail);



    }

}
