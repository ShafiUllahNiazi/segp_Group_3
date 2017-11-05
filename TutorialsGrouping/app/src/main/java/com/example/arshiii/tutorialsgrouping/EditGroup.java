package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class EditGroup extends AppCompatActivity {

    private EditText gName;
    private TextView gTeacher;
    private Toolbar mToolBar;
    private ArrayList<String> mStudentsId;
    public static ArrayList<Student> mGroupStudents;
    private DbHelper mDatabases;
    private String mTeacherId;
    private String mGName;
    private String mGId;
    private ArrayList<String> checkedStd;
    private EditText mGroupName;
    private TextView mTeacherName;
    private String mTechName;
    private Button edit_group_save_btn;
    private Button edit_group_cancel_btn;
    Holding hh=null;
    HoldingStd hhstd=null;
    private String lastestGrpIndex;
    RecyclerView mGroupStudentsRecyclearView;
    public static EditGroupStudentsAdapter editGroupStudentAdapter;
    ImageButton mSelectStudentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        mToolBar = (Toolbar) findViewById(R.id.edit_group_app_bar);
        mGroupName = (EditText) findViewById(R.id.edit_group_edit_name);
        mTeacherName = (TextView) findViewById(R.id.edit_group_instructor_name);
        mSelectStudentBtn = (ImageButton) findViewById(R.id.edit_group_selectStudent_group_btn);
        edit_group_save_btn= (Button) findViewById(R.id.edit_group_save_btn);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Edit");
        edit_group_cancel_btn= (Button) findViewById(R.id.edit_group_cancel_btn);
        edit_group_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit_group_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      if(!mGroupName.getText().toString().isEmpty()){
                          if(hh!=null ){
                              SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
                              db.execSQL("update TEACHER_TABLE set GROUP_NAME = 0  where KEY_ID='"+hh.getTidd()+"'");
                              db.execSQL("update TEACHER_TABLE set GROUP_NAME='"+hh.getGidd()+"' where KEY_ID='"+hh.getNewTid()+"'");
                              db.execSQL("update GROUP_NAM set T_ID='"+hh.getNewTid()+"' where G_ID='"+hh.getGidd()+"'");
                              db.execSQL("update GROUP_NAM set GROUP_NAME='"+mGroupName.getText().toString()+"' where G_ID='"+hh.getGidd()+"'");
                              Toast.makeText(EditGroup.this, "Group Saved", Toast.LENGTH_SHORT).show();

                          }
                          if(hhstd!=null){
                              SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
                              db.execSQL("update TEACHER_TABLE set GROUP_NAME='"+hhstd.getGidd()+"' where KEY_ID='"+hhstd.getTidd()+"'");
                              db.execSQL("update GROUP_NAM set T_ID='"+hhstd.getTidd()+"' where G_ID='"+hhstd.getGidd()+"'");
                              db.execSQL("update GROUP_NAM set GROUP_NAME='"+mGroupName.getText().toString()+"' where G_ID='"+hhstd.getGidd()+"'");
                              int size=checkedStd.size();
                              Cursor c=db.rawQuery("SELECT * FROM GROUP_NAM where G_ID = '"+hhstd.getGidd()+"'", null);
                              int num=0;
                              while (c.moveToNext()){
                                  num= Integer.parseInt(c.getString(3));

                              }
                              num=num+size;
                              db.execSQL("update GROUP_NAM set NO_STUDENT='"+num+"' where G_ID='"+hhstd.getGidd()+"'");
                              for(int i=0;i< checkedStd.size();i++){
                                  db.execSQL("update STUDENT_TABLE set GROUP_ID='"+hhstd.getGidd()+"' where uob='"+checkedStd.get(i)+"'");
                              }
                              Toast.makeText(EditGroup.this, "Changes Saved Succesfully", Toast.LENGTH_SHORT).show();

                          }
                          else{
                              SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
                              db.execSQL("update GROUP_NAM set GROUP_NAME='"+mGroupName.getText().toString()+"' where G_ID='"+mGId+"'");
                              Toast.makeText(EditGroup.this, "UnChange Group Saved", Toast.LENGTH_SHORT).show();
                          }

                          startActivity(new Intent(EditGroup.this,MainActivity.class));
                      }else{
                          Toast.makeText(EditGroup.this, "Group Name is Empty", Toast.LENGTH_SHORT).show();
                      }
            }
        });
        mGroupStudents = new ArrayList<>();
        mStudentsId = new ArrayList<>();
        mDatabases = new DbHelper(this);
        getAllIntentData();
        SetValues();
            getStudents();
            displayStudents();
            selectOtherStudent();
            selectedTeacher();


        mTeacherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectTeacherIntent = new Intent(EditGroup.this,SelectTeachers.class);
                selectTeacherIntent.putExtra("activityName","EditGroup");
                selectTeacherIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Holding hold=new Holding(mGId,mGName,mTeacherId,mTechName);
                for(int i=0;i<mStudentsId.size();i++){
                    hold.studentidd.add(mStudentsId.get(i));
                }
                selectTeacherIntent.putExtra("datasaved", (Serializable) hold);
                startActivity(selectTeacherIntent);
            }
        });
        mSelectStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent selectStudentIntent = new Intent(EditGroup.this,Select_Student.class);
                selectStudentIntent.putExtra("activityName","EditGroup");
                selectStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                HoldingStd holdstd=new HoldingStd(mGId,mGName,mTeacherId,mTechName);
                for(int i=0;i<mStudentsId.size();i++){
                    holdstd.studentidd.add(mStudentsId.get(i));
                }
                selectStudentIntent.putExtra("datasaved", (Serializable) holdstd);
                startActivity(selectStudentIntent);
            }
        });
    }
    public void getAllIntentData(){
        try{

            mStudentsId = getIntent().getStringArrayListExtra("students");
            mTeacherId  = getIntent().getStringExtra("tId");
            mGName   = getIntent().getStringExtra("gName");
            mGId     = getIntent().getStringExtra("gId");
           // Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){

        }

    }
    public void SetValues(){
        mGroupName.setText(mGName);
        mTechName= getTeacherName(mTeacherId);
        mTeacherName.setText(mTechName);
    }
    public String getTeacherName(String id){
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS",MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM TEACHER_TABLE where KEY_ID='"+id+"'", null);
        while(c.moveToNext()){
            return c.getString(2);
        }
        return null;
    }
    public void getStudents(){
        try{
            for(int i=0;i<mStudentsId.size();i++){
                mGroupStudents.add(mDatabases.getSelectedStudent(mStudentsId.get(i)));
            }
        }catch(Exception e){

        }

    }
    private void displayTeacher() {
        try {
            mTeacherId = getIntent().getStringExtra("Teacher_id");
            mTechName = getIntent().getStringExtra("Teacher_Name");
            mTeacherName.setText(mTechName);
            // Toast.makeText(this,teacherId + " "+ teacherName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }
    public void displayStudents(){

        for(int i=0;i<mGroupStudents.size();i++){
        //    Toast.makeText(this,mGroupStudents.get(i).getStdName(), Toast.LENGTH_SHORT).show();
            mGroupStudentsRecyclearView = (RecyclerView) findViewById(R.id.edit_group_show_selected_student_recyclearView);
            editGroupStudentAdapter= new EditGroupStudentsAdapter(mGroupStudents,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mGroupStudentsRecyclearView.setLayoutManager(mLayoutManager);
            mGroupStudentsRecyclearView.setItemAnimator(new DefaultItemAnimator());
            mGroupStudentsRecyclearView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
            mGroupStudentsRecyclearView.setAdapter(editGroupStudentAdapter);
            editGroupStudentAdapter.notifyDataSetChanged();
        }
    }
    public void selectOtherStudent() {
        try {
            checkedStd = getIntent().getStringArrayListExtra("checkedStudent");
            hhstd = (HoldingStd) getIntent().getSerializableExtra("datasavededit");
       //     Toast.makeText(this,hhstd.getGnamed() + " "+ hhstd.getTnamed(), Toast.LENGTH_SHORT).show();
            mStudentsId = hhstd.studentidd;
            mTeacherId  = hhstd.getTidd();
            mGName   = hhstd.getGnamed();
            mGId     = hhstd.getGidd();

            for (int i = 0; i < checkedStd.size(); i++) {
                mStudentsId.add(checkedStd.get(i));
             //   Toast.makeText(this, checkedStd.get(i), Toast.LENGTH_SHORT).show();
               //mGroupStudents.add(mDatabases.getSelectedStudent(checkedStd.get(i)));
            }
            SetValues();
            getStudents();
            displayStudents();
        }catch (Exception e){

        }
    }
    public void selectedTeacher(){
        try {
            String Sss=getIntent().getStringExtra("Teacher_idEdit");
         hh = (Holding) getIntent().getSerializableExtra("datasavededit");

               // Toast.makeText(this,hh.studentidd.get(i), Toast.LENGTH_SHORT).show();
                mStudentsId = hh.studentidd;
                mTeacherId  = hh.getNewTid();
                mGName   = hh.getGnamed();
                mGId     = hh.getGidd();
                SetValues();
                getStudents();
                displayStudents();


         //   Toast.makeText(this,hh.getGnamed() + " "+ hh.getNewTechName(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateStudent(String id){

    }

}
