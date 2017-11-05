package com.example.arshiii.tutorialsgrouping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Group_Member_Profile extends AppCompatActivity {

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
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__member__profile);
        mProfileUob = (TextView) findViewById(R.id.group_student_profile_uob_nbr);
        mStudentYear = (TextView) findViewById(R.id.group_student_profile_year_nbr);
        mStudentGroup = (TextView) findViewById(R.id.group_student_profile_group_nbr);
        mStudentUob = getIntent().getStringExtra("Student_uob");
        groupName = getIntent().getStringExtra("group_Name");
        mDatabase = new DbHelper(this);
        mStudent = new ArrayList<>();
        Toast.makeText(this,mStudentUob, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.group_member_profile_toolbar);
        setSupportActionBar(toolbar);
        studentProfile();
        getSupportActionBar().setTitle(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.group_member_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editStudentIntent = new Intent(Group_Member_Profile.this, EditStudent.class);
                editStudentIntent.putStringArrayListExtra("student", mStudent);
                editStudentIntent.putExtra("activityName","Group_Member_Profile");
                editStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(editStudentIntent);
            }
        });
    }

    private void studentProfile() {

        ArrayList<Student> student = mDatabase.getSingleStudent(mStudentUob);
        mTitle = student.get(0).getStdName();
        mUob = student.get(0).getUob();
        mYear = student.get(0).getStdYear();


        mProfileUob.setText(mUob);
        mStudentYear.setText(mYear);
        mStudentGroup.setText(groupName);
        mStudent.add(mTitle);
        mStudent.add(mUob);
        mStudent.add(mYear);
        mStudent.add(mGroup);


    }
}
