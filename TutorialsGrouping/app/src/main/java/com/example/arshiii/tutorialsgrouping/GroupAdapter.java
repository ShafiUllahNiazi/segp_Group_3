package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Arshiii on 11/1/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder>{
    private ArrayList<GroupData> mGroups;
    DbHelper mDatabase;
    Context cntx;


    public GroupAdapter(ArrayList<GroupData> groups,Context cntx){
        this.mGroups = groups;
        mDatabase = new DbHelper(cntx);
        this.cntx = cntx;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView gName;
        public  TextView teacherName;
        public TextView noOfStudent;


        public MyViewHolder(View itemView) {
            super(itemView);

            gName = (TextView) itemView.findViewById(R.id.main_display_group_name);
            teacherName = (TextView) itemView.findViewById(R.id.main_display_instructor_name);
            noOfStudent = (TextView) itemView.findViewById(R.id.main_display_nbr_of_students);

        }

    }
    @Override
    public GroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_custum_view,parent,false);
        return new GroupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupAdapter.MyViewHolder holder, int position) {
     //  ArrayList<Teacher> teacher = new ArrayList<>();

        GroupData group = mGroups.get(position);
        String tName = getTeacherName(group.getgTid());
        holder.gName.setText(group.getgName());
        holder.teacherName.setText(tName);
        holder.noOfStudent.setText(group.getgCount());

    }
    public String getTeacherName(String id){
        SQLiteDatabase db=cntx.openOrCreateDatabase("TUTORIAL_GROUPS",cntx.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM TEACHER_TABLE where KEY_ID='"+id+"'", null);
        while(c.moveToNext()){
            return c.getString(2);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return mGroups.size();
    }
}
