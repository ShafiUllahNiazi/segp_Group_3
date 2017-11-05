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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreateGroup extends AppCompatActivity {
    private Toolbar mToolBar;
    private ImageButton mSelectStudentBtn;
    private TextView mSelectTeacher;
    private ArrayList<String> checkedStd;
    public static ArrayList<Student> mSelectedStd;
    private RecyclerView mSelectedStudentRecyclearView;
    public static SelectedStdAdapter mSeledStdAdapter;
    private DbHelper mDatabase;
    private Button createGroupBtn;
    private Button cancelBtn;
    private EditText mGroupName;
    private TextView mTeacherName;
    String teacherId=null;
    String teacherName=null;
    private String lastestGrpIndex;
    public static final String PREFERENCEDATA = "DATA" ;
    public static final String GROUP = "GROUP";
    public static final String TEACHER = "TEACHER";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        mToolBar = (Toolbar) findViewById(R.id.create_group_app_bar);
        mSelectTeacher = (TextView) findViewById(R.id.new_group_instructor_name);
        mSelectStudentBtn = (ImageButton) findViewById(R.id.new_group_selectStudent_group_btn);
        mGroupName = (EditText) findViewById(R.id.new_group_edit_name);
        mTeacherName = (TextView) findViewById(R.id.new_group_instructor_name);
        createGroupBtn = (Button) findViewById(R.id.create_group_save_btn);
        cancelBtn = (Button) findViewById(R.id.create_group_cancel_btn);
        mSelectedStd = new ArrayList<>();
        checkedStd = new ArrayList<>();
        mDatabase = new DbHelper(this);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateGroup.this,MainActivity.class));
            }
        });
        displayTeacher();

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Create Group");
        mSelectTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences(PREFERENCEDATA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(GROUP, mGroupName.getText().toString());
                editor.putString(TEACHER, mTeacherName.getText().toString());
                editor.commit();
                Intent selectTeacherIntent = new Intent(CreateGroup.this,SelectTeachers.class);
                selectTeacherIntent.putExtra("activityName","CreateGroup");
                startActivity(selectTeacherIntent);
            }
        });

        mSelectStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectStudentIntent = new Intent(CreateGroup.this,Select_Student.class);
                selectStudentIntent.putExtra("activityName","CreateGroup");
                selectStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(selectStudentIntent);
            }
        });
        mSelectedStd = new ArrayList<>();
        displaySelectedStudents();
        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int count= getMinGroup();
                boolean check=true;
//               if(mGroupName.getText().toString().isEmpty() && mTeacherName.getText().toString().isEmpty() && mSelectedStd.size()==0){
//                   check=false;
//                   Toast.makeText(CreateGroup.this, "Fileds Cannot be Empty", Toast.LENGTH_SHORT).show();
//               }
               if(!mGroupName.getText().toString().isEmpty() && !mTeacherName.getText().toString().isEmpty() &&  mSelectedStd.size()!=0){
                   if(mSelectedStd.size()>8 ){
                        Toast.makeText(CreateGroup.this, "Member Limit Exceeds", Toast.LENGTH_SHORT).show();
                    }
                    else if(count>=2){
                        if(mSelectedStd.size()<=4){
                            Toast.makeText(CreateGroup.this, "Already Two Groups Exists with 4 Members", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String gName = mGroupName.getText().toString();
                            String tName = mTeacherName.getText().toString();
                            //  Toast.makeText(CreateGroup.this, teacherId, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(CreateGroup.this,gName+  " " +teacherId +" "+ mSelectedStd.size(), Toast.LENGTH_SHORT).show();
                            mDatabase.insertGroup(gName,teacherId,String.valueOf(mSelectedStd.size()));
                            lastestGrpIndex = getIndex();
                            upDateStudents();
                            upDateTeacher();
                            Intent mainIntent = new Intent(CreateGroup.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                    }
              //  }
                else{
                    String gName = mGroupName.getText().toString();
                    String tName = mTeacherName.getText().toString();
                 //   Toast.makeText(CreateGroup.this, teacherId, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(CreateGroup.this,gName+  " " +teacherId +" "+ mSelectedStd.size(), Toast.LENGTH_SHORT).show();
                    mDatabase.insertGroup(gName,teacherId,String.valueOf(mSelectedStd.size()));
                    lastestGrpIndex = getIndex();
                    // Toast.makeText(CreateGroup.this, lastestGrpIndex, Toast.LENGTH_SHORT).show();
                    upDateStudents();
                    upDateTeacher();
                       Intent mainIntent = new Intent(CreateGroup.this,MainActivity.class);
                       mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(mainIntent);
                       finish();
                   // Toast.makeText(CreateGroup.this, "Please Enter All Field Correctely", Toast.LENGTH_SHORT).show();
                }

                   }
                   else{
                   Toast.makeText(CreateGroup.this, "Fileds Cannot be Empty", Toast.LENGTH_SHORT).show();
               }
//

            }
        });

    }

    private int getMinGroup() {
       int count =0;
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c= db.rawQuery("select * from GROUP_NAM where NO_STUDENT <= 4",null);
        while(c.moveToNext()){
           count++;


        }
        Toast.makeText(this, ""+ count, Toast.LENGTH_SHORT).show();
        return count;
    }

    private void upDateTeacher() {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        db.execSQL("update TEACHER_TABLE set GROUP_NAME='"+lastestGrpIndex+"' where KEY_ID='"+teacherId+"'");
        Toast.makeText(this, "Data Updated Succesfuly", Toast.LENGTH_SHORT).show();
        sharedpreferences = getSharedPreferences(PREFERENCEDATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("GROUP", "");
        editor.putString("TEACHER","");
        editor.commit();
    }

    public void displaySelectedStudents() {
        sharedpreferences = getSharedPreferences(PREFERENCEDATA, Context.MODE_PRIVATE);
        mGroupName.setText(sharedpreferences.getString(GROUP,null));
        mTeacherName.setText(sharedpreferences.getString(TEACHER,null));
        teacherId= sharedpreferences.getString("TTID",null);
        try{
            checkedStd = getIntent().getStringArrayListExtra("checkedStudent");
            for(int i=0;i<checkedStd.size();i++){
                mSelectedStd.add(mDatabase.getSelectedStudent(checkedStd.get(i)));
            }
            mSelectedStudentRecyclearView = (RecyclerView) findViewById(R.id.create_group_show_selected_student_recyclearView);
            mSeledStdAdapter = new SelectedStdAdapter(mSelectedStd);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mSelectedStudentRecyclearView.setLayoutManager(mLayoutManager);
            mSelectedStudentRecyclearView.setItemAnimator(new DefaultItemAnimator());
            mSelectedStudentRecyclearView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
            mSelectedStudentRecyclearView.setAdapter(mSeledStdAdapter);
            mSeledStdAdapter.notifyDataSetChanged();
        }catch (Exception e){

        }
    }

    private void displayTeacher() {
        try {
            teacherId = getIntent().getStringExtra("Teacher_id");
            teacherName = getIntent().getStringExtra("Teacher_Name");
            mTeacherName.setText(teacherName);
            // Toast.makeText(this,teacherId + " "+ teacherName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    public String getIndex(){
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
       Cursor c= db.rawQuery("select * from GROUP_NAM order by G_ID desc",null);
        while(c.moveToNext()){
          //  Toast.makeText(this,c.getString(0), Toast.LENGTH_SHORT).show();
            return c.getString(0);

        }
        return null;

    }
    public void upDateStudents(){
        for(int i=0;i<mSelectedStd.size();i++){
            updatData(mSelectedStd.get(i).getUob(),lastestGrpIndex);
        }
    }
    private void updatData(String uob,String group) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        db.execSQL("update STUDENT_TABLE set GROUP_ID='"+group+"' where uob='"+uob+"'");
      //
        //  Toast.makeText(this, "Data Updated Succesfuly", Toast.LENGTH_SHORT).show();
    }
}
