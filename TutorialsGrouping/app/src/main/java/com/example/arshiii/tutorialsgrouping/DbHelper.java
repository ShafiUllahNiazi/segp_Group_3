package com.example.arshiii.tutorialsgrouping;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Arshiii on 10/24/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "TUTORIAL_GROUPS";
    private static final int version = 1;
    private static final String TABLE_NAME = "STUDENT_TABLE";
    private static final String STUDENT_NAME = "NAME";
    private static final String STUDENT_UOB = "UOB";
    private static final String STUDENT_YEAR = "YEAR";
    private static final String STUDENT_PROGRAM = "PROGRAM";
    private static final String TEACHER_EMAIL ="EMAIL" ;
    private static final String TEACHER_NAME = "NAME";
    private static final String TCHER_TABLE = "TEACHER_TABLE";
    private static final String GROUP_STATUS = "GROUP_ID";
    private static final String G_STATUS = "GROUP_NAME";
    private static final String TEACHER_ID = "KEY_ID";
    private static final String GROUP_TABLE = "GROUP_NAM";
    private static final String GROUP_ID = "G_ID";
    private static final String GROUP_NAME = "GROUP_NAME";
    private static final String G_TEACHER_NAME = "G_TEACHER_NAM";
    private static final String G_TEACHER_ID = "T_ID";
    private static final String STD_COUNT = "NO_STUDENT";
   // public static  final String TDB_Name="Test";
    private SQLiteDatabase mSqliteDatabase;
    private Context cntx;

    public DbHelper(Context context) {
        super(context, DB_Name, null, version);
        cntx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String studentTable = "CREATE TABLE " + TABLE_NAME + "(" + STUDENT_UOB + " ID INTEGER PRIMARY KEY,"+ STUDENT_NAME + " VARCHAR," + STUDENT_YEAR +
                " VARCHAR,"+ GROUP_STATUS + " INTEGER)";
        String teacherTable = "CREATE TABLE " + TCHER_TABLE + "(" + TEACHER_ID + " INTEGER PRIMARY KEY,"+ TEACHER_EMAIL + " VARCHAR,"+ TEACHER_NAME + " VARCHAR,"+ G_STATUS + " INTEGER)";
        String groupTable   = "CREATE TABLE " + GROUP_TABLE + "(" + GROUP_ID + " INTEGER PRIMARY KEY,"+ GROUP_NAME + " VARCHAR,"+ G_TEACHER_ID + " INTEGER,"+ STD_COUNT + " INTEGER)";
        db.execSQL(studentTable);
        db.execSQL(teacherTable);
        db.execSQL(groupTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
    public void insertStudent(String uob,String stdName,String stdYear,String groupName){

        mSqliteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_UOB,uob);
        values.put(STUDENT_NAME,stdName);
        values.put(STUDENT_YEAR,stdYear);
        values.put(GROUP_STATUS,groupName);
        mSqliteDatabase.insert(TABLE_NAME,null,values);
        mSqliteDatabase.close();
        Intent allStudentIntent = new Intent(cntx,All_Students.class);
        allStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        cntx.startActivity(allStudentIntent);
        Toast.makeText(cntx, "Data entered successfully", Toast.LENGTH_SHORT).show();

    }

    public void insertGroup(String gName,String teacherId,String stdCount){

        Toast.makeText(cntx, gName, Toast.LENGTH_SHORT).show();

        mSqliteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_NAME,gName);
        values.put(G_TEACHER_ID,teacherId);
        values.put(STD_COUNT,stdCount);
        mSqliteDatabase.insert(GROUP_TABLE,null,values);
        mSqliteDatabase.close();
        /*Intent mainIntent = new Intent(cntx,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        cntx.startActivity(mainIntent);*/
        Toast.makeText(cntx, "Data entered successfully", Toast.LENGTH_SHORT).show();

    }

    public void updateStudent(String uob,String stdName,String stdYear,String stdProgram,String groupName,ArrayList<String> previousStd){
       // String []uobb = {previousStd.get(1),previousStd.get(0),previousStd.get(2),previousStd.get(3),previousStd.get(4)};
        mSqliteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_UOB,uob);
        values.put(STUDENT_NAME,stdName);
        values.put(STUDENT_YEAR,stdYear);
        values.put(STUDENT_PROGRAM,stdProgram);
        values.put(GROUP_STATUS,groupName);
        mSqliteDatabase.update(TABLE_NAME,values, STUDENT_UOB + " = ?",new String[] {previousStd.get(1)});
        mSqliteDatabase.close();
        Intent allStudentIntent = new Intent(cntx,All_Students.class);
        allStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        cntx.startActivity(allStudentIntent);
        Toast.makeText(cntx, "Student Updated Successfull", Toast.LENGTH_SHORT).show();

    }
    public void insertTeacher(String teacherName,String teacherEmail,String grpName){
        mSqliteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEACHER_EMAIL,teacherEmail);
        values.put(TEACHER_NAME,teacherName);
        values.put(G_STATUS,grpName);
        mSqliteDatabase.insert(TCHER_TABLE,null,values);
        mSqliteDatabase.close();
        Intent allTeacherIntent = new Intent(cntx,AllTeachers.class);
        allTeacherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        cntx.startActivity(allTeacherIntent);
        Toast.makeText(cntx, "Data entered successfully", Toast.LENGTH_SHORT).show();
    }
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                students.add(student);
            }while (cursor.moveToNext());
        }
       mSqliteDatabase.close();

        return students;
    }

    public ArrayList<Teacher> getAllTeachers() {

        ArrayList<Teacher> teachers = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TCHER_TABLE;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Teacher teacher = new Teacher(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                teachers.add(teacher);
            }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();

        return teachers;
    }

    public ArrayList<Teacher> getSingleTeacher(String id) {
        Toast.makeText(cntx,id, Toast.LENGTH_SHORT).show();

        ArrayList<Teacher> singleTeacher = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TCHER_TABLE + " WHERE " + TEACHER_ID + " = " + id;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Teacher teacher = new Teacher(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                singleTeacher.add(teacher);
            }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();

        return singleTeacher;
    }

    public ArrayList<Student> getOthserStudents(){
        ArrayList<Student> otherStudents = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME + " WHERE " + GROUP_STATUS + " = " + "0";
        //  String query = "Select * from " + TABLE_NAME;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                otherStudents.add(student);
            }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();

        return otherStudents;
    }

    public ArrayList<Teacher> getUngroupedTeachers(){
        ArrayList<Teacher> ungroupedTeachers = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TCHER_TABLE + " WHERE " + G_STATUS + " = " + "0";
        //  String query = "Select * from " + TABLE_NAME;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Teacher teacher = new Teacher(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                ungroupedTeachers.add(teacher);
            }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();

        return ungroupedTeachers;
    }

    public ArrayList<Student> getSingleStudent(String uob){
        ArrayList<Student> singleStudent = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME + " WHERE " + STUDENT_UOB + " = " + uob;
        //  String query = "Select * from " + TABLE_NAME;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                singleStudent.add(student);
            }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();

        return singleStudent;
    }
    public Student getSelectedStudent(String uob){
       // ArrayList<Student> singleStudent = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME + " WHERE " + STUDENT_UOB + " = " + uob;
        //  String query = "Select * from " + TABLE_NAME;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        Student student = null;
        if(cursor.moveToFirst()){
           // do{
              student   = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
           // return student;
            // singleStudent.add(student);
           // }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();
       return student;

    }
    public ArrayList<GroupData> getGroupData(){
        ArrayList<GroupData> groups = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + GROUP_TABLE;
        //  String query = "Select * from " + TABLE_NAME;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                GroupData groupData = new GroupData(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                groups.add(groupData);
            }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();
        return  groups;
    }

    public ArrayList<Student> getStudentsByGid(String gId){


        ArrayList<Student> groupStudents = new ArrayList<>();
        mSqliteDatabase = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME + " WHERE " + GROUP_STATUS + " = " + gId;
        //  String query = "Select * from " + TABLE_NAME;
        Cursor cursor = mSqliteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                groupStudents.add(student);
            }while (cursor.moveToNext());
        }
        mSqliteDatabase.close();

        return groupStudents;
    }


    public void updateTeacher(String mTeacherId, String name, String email) {

        mSqliteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEACHER_EMAIL,email);
        values.put(TEACHER_NAME,name);
        mSqliteDatabase.update(TCHER_TABLE,values, TEACHER_ID + " = ?",new String[] {mTeacherId});
        mSqliteDatabase.close();
        Intent allStudentIntent = new Intent(cntx,AllTeachers.class);
        allStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        cntx.startActivity(allStudentIntent);
        Toast.makeText(cntx, "Data Updated Successfull", Toast.LENGTH_SHORT).show();
    }

    public void deletStudent(String id) {
        mSqliteDatabase = getReadableDatabase();
        mSqliteDatabase.delete(TABLE_NAME,STUDENT_UOB + " = ?",new String[] { String.valueOf(id)});
        All_Students.mStudentsAdapter.notifyDataSetChanged();
    }
    public void deletTeacher(String id) {
        mSqliteDatabase = getReadableDatabase();
        mSqliteDatabase.delete(TCHER_TABLE,TEACHER_ID + " = ?",new String[] { String.valueOf(id)});
    }
    public void deletGroup(String id) {
        mSqliteDatabase = getReadableDatabase();
        mSqliteDatabase.delete(GROUP_TABLE,GROUP_ID + " = ?",new String[] { String.valueOf(id)});
    }


}