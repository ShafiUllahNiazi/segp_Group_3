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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {

    private TextView mStudentName;
    private String mStudentUob;
    private DbHelper mDatabase;
    private String mTitle;
    private TextView mProfileUob;
    private TextView mStudentYear;
    private TextView mStudentGroup;
    private String mUob;
    private String mGroup;
    private String mYear;
    private ArrayList<String> mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.student_profile_app_bar);
        setSupportActionBar(toolbar);
        mProfileUob = (TextView) findViewById(R.id.student_profile_uob_nbr) ;
        mStudentYear = (TextView) findViewById(R.id.student_profile_year_nbr);
        mStudentGroup = (TextView) findViewById(R.id.student_profile_group_nbr);
        mStudentUob = getIntent().getStringExtra("Student_uob");
        mDatabase = new DbHelper(this);
        mStudent = new ArrayList<>();
        studentProfile();
        getSupportActionBar().setTitle(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.student_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editStudentIntent = new Intent(StudentProfile.this,EditStudent.class);
                editStudentIntent.putStringArrayListExtra("student",mStudent);
                editStudentIntent.putExtra("activityName","Group_Member_Profile");
                editStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(editStudentIntent);
                finish();
            }
        });
    }

    private void studentProfile() {

        ArrayList<Student> student = mDatabase.getSingleStudent(mStudentUob);
        mTitle = student.get(0).getStdName();
        mUob = student.get(0).getUob();
        mYear = student.get(0).getStdYear();
        mGroup = student.get(0).getGroupName();
        Toast.makeText(this, mGroup, Toast.LENGTH_SHORT).show();
        if(mGroup.equals("0")){
            mGroup = "No group";
        }
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM GROUP_NAM where G_ID = '"+mGroup+"'", null);
        while (c.moveToNext()){
            mGroup = c.getString(1);

        }

        mProfileUob.setText(mUob);
        mStudentYear.setText(mYear);
        mStudentGroup.setText(mGroup);
        mStudent.add(mTitle);
        mStudent.add(mUob);
        mStudent.add(mYear);
        mStudent.add(mGroup);




    }

}
