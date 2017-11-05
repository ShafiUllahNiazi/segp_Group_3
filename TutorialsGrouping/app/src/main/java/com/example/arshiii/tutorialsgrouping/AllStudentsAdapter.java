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

public class AllStudentsAdapter extends RecyclerView.Adapter<AllStudentsAdapter.MyViewHolder>{
    private ArrayList<Student> students;

    public AllStudentsAdapter(ArrayList<Student> students){
          this.students = students;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public  TextView uob;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.all_student_custum_name);
            uob = (TextView) itemView.findViewById(R.id.all_student_custum_uob);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_student_custum_view,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Student student = students.get(position);
        holder.name.setText(student.getStdName());
        holder.uob.setText(student.getUob());

    }


    @Override
    public int getItemCount() {
        return students.size();
    }
}
