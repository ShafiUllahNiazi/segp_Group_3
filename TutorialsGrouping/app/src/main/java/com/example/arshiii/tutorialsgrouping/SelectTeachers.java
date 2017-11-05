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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectTeachers extends AppCompatActivity {

    Toolbar mToolBar;
    ArrayList<Teacher> mTeachers;
    DbHelper mDatabase;
    RecyclerView mSelectTeachersRecyclearView;
    private SelectTeacherAdapter mTeachersAdapter;
    private Button mSaveBtn;
    private Button mCancelBtn;
    private CheckBox mCheckBox;
    public static final String PREFERENCEDATA = "DATA" ;
    SharedPreferences sharedpreferences;
    Holding hh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_teachers);

        mSaveBtn = (Button) findViewById(R.id.Select_teacher_save_btn);
        mCancelBtn = (Button) findViewById(R.id.Select_teacher_cancel_btn);
        mCheckBox = (CheckBox) findViewById(R.id.select_student_custum_checkbox);
        mToolBar = (Toolbar) findViewById(R.id.select_teacher_app_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Select Teacher");
        mDatabase = new DbHelper(this);
        displayTeachers();
        getData();
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTeacher();
                Intent createGrpIntent = new Intent(SelectTeachers.this,CreateGroup.class);
                createGrpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(createGrpIntent);
            }
        });
    }

    private void selectedTeacher() {
    }

    private void displayTeachers() {

        mTeachers = mDatabase.getUngroupedTeachers();
        for(int i=0;i<mTeachers.size();i++){
            //  Toast.makeText(this,mTeachers.get(i).getmTeacherName(), Toast.LENGTH_SHORT).show();
        }
        mSelectTeachersRecyclearView = (RecyclerView) findViewById(R.id.select_select_teacher_recyclear_view);
        mTeachersAdapter = new SelectTeacherAdapter(mTeachers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mSelectTeachersRecyclearView.setLayoutManager(mLayoutManager);
        mSelectTeachersRecyclearView.setItemAnimator(new DefaultItemAnimator());
        mSelectTeachersRecyclearView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mSelectTeachersRecyclearView.setAdapter(mTeachersAdapter);
        mTeachersAdapter.notifyDataSetChanged();
        mSelectTeachersRecyclearView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),  mSelectTeachersRecyclearView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
              //  Toast.makeText(SelectTeachers.this, mTeachers.get(position).getmTeacherName(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(SelectTeachers.this,  mTeachers.get(position).getmTeacherName(), Toast.LENGTH_SHORT).show();
                if(getIntent().getStringExtra("activityName").equals("CreateGroup")){
                    sharedpreferences = getSharedPreferences(PREFERENCEDATA, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("TEACHER",mTeachers.get(position).getmTeacherName() );
                    editor.putString("TTID",mTeachers.get(position).getKeyId() );
                    editor.commit();

                    Intent teacherProfileIntnet = new Intent(SelectTeachers.this,CreateGroup.class);
                    teacherProfileIntnet.putExtra("Teacher_id",mTeachers.get(position).getKeyId());
                    teacherProfileIntnet.putExtra("Teacher_Name",mTeachers.get(position).getmTeacherName());
                    startActivity(teacherProfileIntnet);
                }
                else if(getIntent().getStringExtra("activityName").equals("EditGroup")){
                    Intent teacherProfileIntnet = new Intent(SelectTeachers.this,EditGroup.class);
                    hh.setNewTid(mTeachers.get(position).getKeyId());
                    hh.setNewTechName(mTeachers.get(position).getmTeacherName());
                    teacherProfileIntnet.putExtra("datasavededit", (Serializable) hh);
//                   teacherProfileIntnet.putExtra("Teacher_idEdit",mTeachers.get(position).getKeyId());
//                   teacherProfileIntnet.putExtra("Teacher_NameEdit",mTeachers.get(position).getmTeacherName());
//                    teacherProfileIntnet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(teacherProfileIntnet);
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }) {
        });
    }
    private void getData(){
        try {
            if(getIntent().getStringExtra("activityName").equals("EditGroup")){
               // Toast.makeText(this, "Helloffffff", Toast.LENGTH_SHORT).show();
             hh = (Holding) getIntent().getSerializableExtra("datasaved");
               // Toast.makeText(this, hh.getGnamed()+"   " + hh.getTnamed(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
    }
}
