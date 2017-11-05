package com.example.arshiii.tutorialsgrouping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Group_View extends AppCompatActivity {

    Toolbar mToolBar;
    private String mGroupId;
    private DbHelper mDatabase;
    private ArrayList<Student> groupMembers;
    private String mGroupName;
    private RecyclerView mGroupStudentsRecyclearView;
    private AllStudentsAdapter mStudentsAdapter;
    private ArrayList<String> mGroupStudents;
    private String mTeacherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__view);
        mGroupId = getIntent().getStringExtra("Group_Id");
        mGroupName = getIntent().getStringExtra("Group_name");
        mTeacherID = getIntent().getStringExtra("tId");
        mDatabase = new DbHelper(this);
        mToolBar = (Toolbar) findViewById(R.id.group_view_app_bar);
        mGroupStudents = new ArrayList<>();
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(mGroupName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getGroupMembers();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.group_view_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editGroupIntent = new Intent(Group_View.this,EditGroup.class);
                editGroupIntent.putStringArrayListExtra("students",mGroupStudents);
                editGroupIntent.putExtra("gName",mGroupName);
                editGroupIntent.putExtra("gId",mGroupId);
                editGroupIntent.putExtra("tId",mTeacherID);
                startActivity(editGroupIntent);
            }
        });
    }

    public void getGroupMembers(){
        groupMembers = mDatabase.getStudentsByGid(mGroupId);
        for(int i=0;i<groupMembers.size();i++){
            mGroupStudents.add(groupMembers.get(i).getUob());
            //Toast.makeText(this,groupMembers.get(i).getUob(), Toast.LENGTH_SHORT).show();
        }

        if(groupMembers.size()>0){

            mGroupStudentsRecyclearView = (RecyclerView) findViewById(R.id.Indiviual_group_members_recyclearView);
            mStudentsAdapter = new AllStudentsAdapter(groupMembers);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mGroupStudentsRecyclearView.setLayoutManager(mLayoutManager);
            mGroupStudentsRecyclearView.setItemAnimator(new DefaultItemAnimator());
            mGroupStudentsRecyclearView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
            mGroupStudentsRecyclearView.setAdapter(mStudentsAdapter);
            mStudentsAdapter.notifyDataSetChanged();
            mGroupStudentsRecyclearView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mGroupStudentsRecyclearView, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            Intent studentProfileIntnet = new Intent(Group_View.this,Group_Member_Profile.class);
                            studentProfileIntnet.putExtra("Student_uob",groupMembers.get(position).getUob());
                            studentProfileIntnet.putExtra("group_Name",mGroupName);
                            startActivity(studentProfileIntnet);

                        }

                        @Override
                        public void onLongClick(View view,final int position) {


                        }
                    })
            );

        }
        else{
            Toast.makeText(this, "No Member to show", Toast.LENGTH_SHORT).show();
        }


    }

}
