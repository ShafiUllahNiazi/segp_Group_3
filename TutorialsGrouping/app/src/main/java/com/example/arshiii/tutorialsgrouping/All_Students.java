package com.example.arshiii.tutorialsgrouping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class All_Students extends AppCompatActivity {

    Toolbar mToolBar;
    SQLiteDatabase db;
    ArrayList<Student> mStudents;
    DbHelper mDatabase;
    RecyclerView mAllStudentsRecyclearView;
    public static AllStudentsAdapter mStudentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__students);
        mDatabase = new DbHelper(this);
        mToolBar = (Toolbar) findViewById(R.id.all_students_app_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("All Students");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayStudents();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addStudentIntent = new Intent(All_Students.this,AddStudent.class);
                addStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(addStudentIntent);
            }
        });
        final FloatingActionButton fabPrint = (FloatingActionButton) findViewById(R.id.fabPrint);
        fabPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStudents = mDatabase.getAllStudents();
                if(mStudents.size()>0){

//create document object
                    Document document=new Document();
                    Random random=new Random();
                    int i=  random.nextInt();
                    String path="/StudentList'"+i+"'.pdf";
//output file path
                    String filepath= Environment.getExternalStorageDirectory()+path;
                    try {
//create pdf writer instance
                        PdfWriter.getInstance(document, new FileOutputStream(filepath));
//open the document for writing
                        document.open();
//add paragraph to the document
                        document.add(new Paragraph("Students Data" +"\n\n\n\n"));
                        for(int j=0;j<mStudents.size();j++){
                            document.add(new Paragraph("Sr. No: "+ j +"\n"));
                            document.add(new Paragraph("Name: "+ mStudents.get(j).getStdName() +"\n"));
                            document.add(new Paragraph("UOB: "+ mStudents.get(j).getUob() +"\n"));
                            if(mStudents.get(j).getGroupName().equals("0")){
                                document.add(new Paragraph("GROUP: "+ "No group" +"\n"));
                            }
                            else{
                                String groupName=getGroupName(mStudents.get(j).getGroupName());
                                document.add(new Paragraph("GROUP: "+groupName+"\n"));
                            }

                            document.add(new Paragraph("--------------" +"\n\n\n\n"));
                            // document.add(new Paragraph( +"\n"));

                        }
                        fabPrint.setEnabled(false);
//close the document
                        document.close();
                        Toast.makeText(All_Students.this, "Document Created Succesfuly", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (DocumentException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(All_Students.this, "No data to Print", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void displayStudents() {

        mStudents = mDatabase.getAllStudents();
        if(mStudents.size()>0){

            mAllStudentsRecyclearView = (RecyclerView) findViewById(R.id.all_student_recyclearView);
            mStudentsAdapter = new AllStudentsAdapter(mStudents);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mAllStudentsRecyclearView.setLayoutManager(mLayoutManager);
            mAllStudentsRecyclearView.setItemAnimator(new DefaultItemAnimator());
            mAllStudentsRecyclearView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
            mAllStudentsRecyclearView.setAdapter(mStudentsAdapter);
            mStudentsAdapter.notifyDataSetChanged();
            mAllStudentsRecyclearView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mAllStudentsRecyclearView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent studentProfileIntnet = new Intent(All_Students.this,StudentProfile.class);
                    studentProfileIntnet.putExtra("Student_uob",mStudents.get(position).getUob());
                    startActivity(studentProfileIntnet);
                }

                @Override
                public void onLongClick(View view,final int position) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(All_Students.this);
                    alertDialogBuilder.setMessage("Do You want to Delete this Student?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
//                                    Toast.makeText(All_Students.this,mStudents.get(position).getGroupName() , Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(All_Students.this,mStudents.get(position).getStdName(),Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(All_Students.this,mStudents.get(position).getUob(), Toast.LENGTH_SHORT).show();
                                    SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
                                  mDatabase.deletStudent(mStudents.get(position).getUob());
                                     Cursor c=db.rawQuery("SELECT * FROM GROUP_NAM where G_ID = '"+mStudents.get(position).getGroupName()+"'", null);
                                      int num=0;
                                    while (c.moveToNext()){
                                   num= Integer.parseInt(c.getString(3));

                                }
                                num=num-1;
                                //    Toast.makeText(All_Students.this, num+ "", Toast.LENGTH_SHORT).show();
                                    db.execSQL("update GROUP_NAM set NO_STUDENT='"+num+"' where G_ID='"+mStudents.get(position).getGroupName()+"'");
                                    Toast.makeText(All_Students.this, "Student Deleted", Toast.LENGTH_SHORT).show();
                                    mStudents.remove(position);
                                    mStudentsAdapter.notifyDataSetChanged();


                                }
                            });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            })
            );

        }
        else{
            Toast.makeText(this, "No student to show", Toast.LENGTH_SHORT).show();
        }





    }
    private String getGroupName(String id) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM GROUP_NAM where G_ID='"+id+"'", null);
        while (c.moveToNext()){
            return  c.getString(1);
        }
        return "";
    }


}
