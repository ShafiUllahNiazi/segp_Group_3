package com.example.arshiii.tutorialsgrouping;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arshiii on 10/28/2017.
 */

public class SelectStudentAdapter extends RecyclerView.Adapter<SelectStudentAdapter.MyViewHolder>{

private ArrayList<Student> students;
    private ArrayList<Student> checked;

public SelectStudentAdapter(ArrayList<Student> students){
        this.students = students;
        }

public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView mName;
    public  TextView mUob;
    public CheckBox mCheckBox;

    public MyViewHolder(View itemView) {
        super(itemView);

        mName = (TextView) itemView.findViewById(R.id.selct_Student_custum_name);
        mUob = (TextView) itemView.findViewById(R.id.select_student_custum_uob);
        mCheckBox = (CheckBox) itemView.findViewById(R.id.select_student_custum_checkbox);
       // mCheckBox.setChecked(false);
        checked = new ArrayList<>();

    }

}
    @Override
    public SelectStudentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_student_list_item_view,parent,false);
        return new SelectStudentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectStudentAdapter.MyViewHolder holder, final int position) {

        final Student student = students.get(position);
        holder.mName.setText(student.getStdName());
        holder.mUob.setText(student.getUob());
       // for(int i=0;i<students.size()){
       //     if(student.getUob().equals(sltedStudent))
       // }

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    holder.mCheckBox.setChecked(true);
                    checked.add(student);
                }

               else {
                    holder.mCheckBox.setChecked(false);
                    for(int i=0;i<checked.size();i++){
                        if(checked.get(i)==student);
                        checked.remove(i);
                    }

                 }


            }
        });

    }
    public ArrayList<Student> getCheckedStd(){
        return checked;
    }


    @Override
    public int getItemCount() {
        return students.size();
    }

}
