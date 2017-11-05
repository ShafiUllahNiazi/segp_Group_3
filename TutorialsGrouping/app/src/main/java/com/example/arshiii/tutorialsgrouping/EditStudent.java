package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditStudent extends AppCompatActivity {

    private Toolbar mToolBar;
    private ArrayList<String> mStudent;
    private EditText mName;
    private EditText mUob;
    private EditText mYear;
    private TextView mGroup;
    private Button mSaveBtn;
    private Button mCancelBtn;
    private DbHelper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        mToolBar = (Toolbar) findViewById(R.id.edit_student_app_bar);
        mName = (EditText) findViewById(R.id.edit_student_edit_name);
        mUob = (EditText) findViewById(R.id.edit_student_uob_nbr);
        mYear = (EditText) findViewById(R.id.edit_student_year_nbr);
        mSaveBtn = (Button) findViewById(R.id.edit_student_save_btn);
        mCancelBtn = (Button) findViewById(R.id.edit_student_cancel_btn);
        database = new DbHelper(this);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Edit Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mStudent = getIntent().getStringArrayListExtra("student");
        mUob.setEnabled(false);
        setValues();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdate();
                Intent allStudent = new Intent(EditStudent.this,MainActivity.class);
                allStudent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(allStudent);
                finish();
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allStudent = new Intent(EditStudent.this,MainActivity.class);
                allStudent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(allStudent);
                finish();
            }
        });

    }

    private void setValues() {

        mName.setText(mStudent.get(0));
        mUob.setText(mStudent.get(1));
        mYear.setText(mStudent.get(2));

    }

    private void saveUpdate() {

        String name = mName.getText().toString();
        String uob = mUob.getText().toString();
        String year = mYear.getText().toString();
        //String group=mGroup.getText().toString();
        if(!uob.isEmpty() && !name.isEmpty() && !year.isEmpty()){
            updatData(uob,name,year);

        }
        else{Toast.makeText(EditStudent.this, "Fill All The Fields", Toast.LENGTH_SHORT).show();}
    }

    private void updatData(String uob, String name, String year) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        db.execSQL("update STUDENT_TABLE set name='"+name+"', year='"+year+"' where uob='"+uob+"'");
        Toast.makeText(this, "Data Updated Succesfuly", Toast.LENGTH_SHORT).show();
    }



}
