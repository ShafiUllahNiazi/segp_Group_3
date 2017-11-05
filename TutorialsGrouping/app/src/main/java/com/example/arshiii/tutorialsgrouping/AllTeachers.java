package com.example.arshiii.tutorialsgrouping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class AllTeachers extends AppCompatActivity {

    private ArrayList<Teacher> mTeachers;
    private DbHelper mDatabase;
    Toolbar mToolbar;
    private RecyclerView mRecyclearView;
    private TeacherAdapter mTeacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teachers);
        mToolbar= (Toolbar) findViewById(R.id.all_teachers_toolbar);
        mDatabase = new DbHelper(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Teachers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayStudents();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.all_teachers_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTeacherIntent = new Intent(AllTeachers.this,AddTeacher.class);
                addTeacherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(addTeacherIntent);
            }
        });
        final FloatingActionButton fabTeacherPrint = (FloatingActionButton) findViewById(R.id.fabPrintTeacher);
        fabTeacherPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTeachers = mDatabase.getAllTeachers();
                if(mTeachers.size()>0){
                    //create document object
                    Document document=new Document();
                    Random random=new Random();
                    int i=  random.nextInt();
                    String path="/TeacherList'"+i+"'.pdf";
//output file path
                    String filepath= Environment.getExternalStorageDirectory()+path;
                    try {
//create pdf writer instance
                        PdfWriter.getInstance(document, new FileOutputStream(filepath));
//open the document for writing
                        document.open();
//add paragraph to the document
                        document.add(new Paragraph("Teachers Data" +"\n\n\n\n"));
                        for(int j=0;j<mTeachers.size();j++){
                            document.add(new Paragraph("Sr. No: "+ j +"\n"));
                            document.add(new Paragraph("Name: "+ mTeachers.get(j).getmTeacherName() +"\n"));
                            document.add(new Paragraph("Email: "+ mTeachers.get(j).getmTeacherEmail() +"\n"));
                            if(mTeachers.get(j).getmGroupName().equals("0")){
                                document.add(new Paragraph("GROUP: "+ "No group" +"\n"));
                            }
                            else{
                                String groupName=getGroupName(mTeachers.get(j).getmGroupName());
                                document.add(new Paragraph("GROUP: "+groupName+"\n"));
                            }

                            document.add(new Paragraph("--------------" +"\n\n\n\n"));
                            // document.add(new Paragraph( +"\n"));

                        }
                        fabTeacherPrint.setEnabled(false);
//close the document
                        document.close();
                        Toast.makeText(AllTeachers.this, "Document Created Succesfuly", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (DocumentException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(AllTeachers.this, "No data to Print", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private String getGroupName(String id) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM GROUP_NAM where G_ID='"+id+"'", null);
        while (c.moveToNext()){
            return  c.getString(1);
        }
        return "";
    }

    private void displayStudents() {

        mTeachers = mDatabase.getAllTeachers();
        mRecyclearView = (RecyclerView) findViewById(R.id.all_teachers_recyclear_view);
        mTeacherAdapter = new TeacherAdapter(mTeachers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclearView.setLayoutManager(mLayoutManager);
        mRecyclearView.setItemAnimator(new DefaultItemAnimator());
        mRecyclearView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclearView.setAdapter(mTeacherAdapter);
        mTeacherAdapter.notifyDataSetChanged();

        mRecyclearView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),  mRecyclearView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent teacherProfileIntnet = new Intent(AllTeachers.this,TeacherProfile.class);
                teacherProfileIntnet.putExtra("Teacher_id",mTeachers.get(position).getKeyId());
                startActivity(teacherProfileIntnet);
            }

            @Override
            public void onLongClick(View view,final int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AllTeachers.this);
                alertDialogBuilder.setMessage("Do You want to Delete this Student?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
//                   Toast.makeText(AllTeachers.this,mTeachers.get(position).getKeyId(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(AllTeachers.this,mTeachers.get(position).getmTeacherEmail(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(AllTeachers.this, mTeachers.get(position).getmGroupName(), Toast.LENGTH_SHORT).show();
                                mDatabase.deletTeacher(mTeachers.get(position).getKeyId());
                                mDatabase.deletGroup(mTeachers.get(position).getmGroupName());
                                SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
                                Cursor c=db.rawQuery("SELECT * FROM STUDENT_TABLE where GROUP_ID='"+mTeachers.get(position).getmGroupName()+"'", null);
                                while (c.moveToNext())
                                {
                                    String id=c.getString(0);
                                    db.execSQL("update STUDENT_TABLE set GROUP_ID = 0 where uob='"+id+"'");
                                    Toast.makeText(AllTeachers.this, "Teacher Deleted", Toast.LENGTH_SHORT).show();


                                }
                                mTeachers.remove(position);
                                mTeacherAdapter.notifyDataSetChanged();

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

}
