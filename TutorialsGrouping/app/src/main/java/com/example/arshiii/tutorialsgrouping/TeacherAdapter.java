package com.example.arshiii.tutorialsgrouping;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arshiii on 10/28/2017.
 */

class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder>{
    private ArrayList<Teacher> mTeachersList;

    public TeacherAdapter(ArrayList<Teacher> teachersList){
        mTeachersList = teachersList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public  TextView email;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.all_student_custum_name);
            email = (TextView) itemView.findViewById(R.id.all_student_custum_uob);

        }

    }
    @Override
    public TeacherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_student_custum_view,parent,false);
        return new TeacherAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeacherAdapter.MyViewHolder holder, int position) {

        Teacher teacher = mTeachersList.get(position);
        holder.name.setText(teacher.getmTeacherName());
        holder.email.setText(teacher.getmTeacherEmail());

    }


    @Override
    public int getItemCount() {
        return mTeachersList.size();
    }
}
