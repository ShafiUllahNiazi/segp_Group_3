package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arshiii on 10/31/2017.
 */

public class SelectedStdAdapter extends RecyclerView.Adapter<SelectedStdAdapter .MyViewHolder>{

    private ArrayList<Student> students;


    public SelectedStdAdapter (ArrayList<Student> students){
        this.students = students;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mName;
        public  TextView mUob;
        public ImageButton deleteBtn;
        public CheckBox mCheckBox;

        public MyViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.create_group_studentselected_student_name);
          //  mUob = (TextView) itemView.findViewById(R.id.select_student_custum_uob);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.create_group_delete_selected_student_btn);



        }

    }
    @Override
    public SelectedStdAdapter .MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_student_custum_layout,parent,false);
        return new SelectedStdAdapter .MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectedStdAdapter.MyViewHolder holder, final int position) {

        final Student student = students.get(position);
        holder.mName.setText(student.getStdName());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // students.remove(position);
                CreateGroup.mSelectedStd.remove(position);
                CreateGroup.mSeledStdAdapter.notifyDataSetChanged();
            }
        });

    }



    @Override
    public int getItemCount() {
        return students.size();
    }

}


