package com.example.arshiii.tutorialsgrouping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;


public class EditTeacher extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private Toolbar mToolBar;
    private ArrayList<String> mTeacher;
    private String mTeacherId;
    private Button mSaveBtn;
    private DbHelper mDatabase;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher);
        mName = (EditText) findViewById(R.id.edit_teacher_name);
        mEmail = (EditText) findViewById(R.id.edit_teacher_email_id);
        mToolBar = (Toolbar) findViewById(R.id.edit_teacher_app_bar);
        mSaveBtn = (Button) findViewById(R.id.edit_teacher_save_btn);
        mDatabase = new DbHelper(this);
        mTeacherId = null;
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Edit Teacher");
        mTeacher = new ArrayList<>();
        setValues();
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeacherUpdate();
            }
        });
        cancelBtn = (Button) findViewById(R.id.edit_teacher_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allStudentIntent = new Intent(EditTeacher.this,AllTeachers.class);
                startActivity(allStudentIntent);
            }
        });


    }

    private void setValues() {

        mTeacher = getIntent().getStringArrayListExtra("teacher");
        mName.setText(mTeacher.get(1));
        mEmail.setText(mTeacher.get(2));
        mTeacherId = mTeacher.get(0);
    }

    private void saveTeacherUpdate() {

        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        if(!email.isEmpty() && !name.isEmpty()){

            mDatabase.updateTeacher(mTeacherId,name,email);
        }
        else{
            Toast.makeText(EditTeacher.this, "Fill All The Fields", Toast.LENGTH_SHORT).show();}
    }
}
