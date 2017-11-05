package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class EditGroupSelectTeachers extends AppCompatActivity {

    Toolbar mToolBar;
    ArrayList<Teacher> mTeachers;
    DbHelper mDatabase;
    RecyclerView meditSelectTeachersRecyclearView;
    private SelectTeacherAdapter mTeachersAdapter;
    private CheckBox mCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_select_teachers);
        mCheckBox = (CheckBox) findViewById(R.id.select_student_custum_checkbox);
        mToolBar = (Toolbar) findViewById(R.id.edit_select_teacher_app_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Select Student");
        mDatabase = new DbHelper(this);
        displayTeachers();
    }

    private void displayTeachers() {

        mTeachers = mDatabase.getUngroupedTeachers();
        for (int i = 0; i < mTeachers.size(); i++) {
            //  Toast.makeText(this,mTeachers.get(i).getmTeacherName(), Toast.LENGTH_SHORT).show();
        }
        meditSelectTeachersRecyclearView = (RecyclerView) findViewById(R.id.edit_select_select_teacher_recyclear_view);
        mTeachersAdapter = new SelectTeacherAdapter(mTeachers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        meditSelectTeachersRecyclearView.setLayoutManager(mLayoutManager);
        meditSelectTeachersRecyclearView.setItemAnimator(new DefaultItemAnimator());
        meditSelectTeachersRecyclearView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        meditSelectTeachersRecyclearView.setAdapter(mTeachersAdapter);
        mTeachersAdapter.notifyDataSetChanged();
        meditSelectTeachersRecyclearView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),  meditSelectTeachersRecyclearView, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Intent teacherProfileIntnet = new Intent(EditGroupSelectTeachers.this,EditGroup.class);
                        startActivity(teacherProfileIntnet);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );
    }
}
