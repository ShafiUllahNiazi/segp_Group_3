package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Select_Student extends AppCompatActivity {

    Toolbar mToolBar;
    ArrayList<Student> mStudents;
    DbHelper mDatabase;
    RecyclerView mSelectStudentsRecyclearView;
    private SelectStudentAdapter mStudentsAdapter;
    private Button mSaveBtn;
    private Button mCanccelBtn;
    private CheckBox mCheckBox;
    private ArrayList<String> checkedStd;
    HoldingStd hh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__student);
        mSaveBtn = (Button) findViewById(R.id.Select_student_save_btn);
        mCanccelBtn = (Button) findViewById(R.id.Select_student_cancel_btn);
        mCheckBox = (CheckBox) findViewById(R.id.select_student_custum_checkbox);
        mToolBar = (Toolbar) findViewById(R.id.select_student_app_bar);
        setSupportActionBar(mToolBar);
        checkedStd = new ArrayList<>();
        //LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View custmView = inflater.inflate(R.layout.select_student_custm_app_bar,null);
       // getSupportActionBar().setCustomView(custmView);
        getSupportActionBar().setTitle("Select Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = new DbHelper(this);
        displayStudents();
        getData();
        mCanccelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedStudent();
                if(getIntent().getStringExtra("activityName").equals("EditGroup")){
                    Intent createGrpIntent = new Intent(Select_Student.this,EditGroup.class);
                    createGrpIntent.putStringArrayListExtra("checkedStudent",checkedStd);
                    createGrpIntent.putExtra("datasavededit", (Serializable) hh);
                    createGrpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(createGrpIntent);
//                    finish();
                }
                else if(getIntent().getStringExtra("activityName").equals("CreateGroup")){
                    Intent createGrpIntent = new Intent(Select_Student.this,CreateGroup.class);
                    createGrpIntent.putStringArrayListExtra("checkedStudent",checkedStd);
                    createGrpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(createGrpIntent);

                }

            }
        });
    }




    private void displayStudents() {

        mStudents = mDatabase.getOthserStudents();
       /* for(int i=0;i<mStudents.size();i++){
            Toast.makeText(this,mStudents.get(i).getStdName(), Toast.LENGTH_SHORT).show();
        }*/
        mSelectStudentsRecyclearView = (RecyclerView) findViewById(R.id.select_student_recyclear_view);
        mStudentsAdapter = new SelectStudentAdapter(mStudents);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mSelectStudentsRecyclearView.setLayoutManager(mLayoutManager);
        mSelectStudentsRecyclearView.setItemAnimator(new DefaultItemAnimator());
        mSelectStudentsRecyclearView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mSelectStudentsRecyclearView.setAdapter(mStudentsAdapter);
        mStudentsAdapter.notifyDataSetChanged();
    }
    private void selectedStudent() {
        try{
            ArrayList<Student> checked  = mStudentsAdapter.getCheckedStd();
            if(checked.size()>0){
                for(int i=0;i<checked.size();i++){
                    checkedStd.add(checked.get(i).getUob());
                }

            }
        }catch(Exception e){

        }



    }
    private void getData(){
        try {
            if(getIntent().getStringExtra("activityName").equals("EditGroup")){
                // Toast.makeText(this, "Helloffffff", Toast.LENGTH_SHORT).show();
                hh = (HoldingStd) getIntent().getSerializableExtra("datasaved");
               // Toast.makeText(this, hh.getGnamed()+"   " + hh.getTnamed(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
    }


}
