package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Arshiii on 10/31/2017.
 */

public class SelectTeacherAdapter extends RecyclerView.Adapter<SelectTeacherAdapter.MyViewHolder>{

    private ArrayList<Teacher> teachers;

    public SelectTeacherAdapter(ArrayList<Teacher> teachers){
        this.teachers = teachers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mName;
        public  TextView mEmail;
        public CheckBox mCheckBox;
        public Context context;

        public MyViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.selct_Student_custum_name);
            mEmail = (TextView) itemView.findViewById(R.id.select_student_custum_uob);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.select_student_custum_checkbox);
            mCheckBox.setVisibility(View.INVISIBLE);


        }

    }
    @Override
    public SelectTeacherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_student_list_item_view,parent,false);
        return new SelectTeacherAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectTeacherAdapter.MyViewHolder holder, final int position) {

        Teacher teacher = teachers.get(position);
        holder.mName.setText(teacher.getmTeacherName());
        holder.mEmail.setText(teacher.getmTeacherEmail());




    }


    @Override
    public int getItemCount() {
        return teachers.size();
    }
}
