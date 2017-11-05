package com.example.arshiii.tutorialsgrouping;

        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

/**
 * Created by Arshiii on 11/2/2017.
 */

public class EditGroupStudentsAdapter extends RecyclerView.Adapter<EditGroupStudentsAdapter .MyViewHolder> {

    private ArrayList<Student> students;
    private DbHelper mDatabases;
    private Context cntx;


    public EditGroupStudentsAdapter(ArrayList<Student> students,Context cntx) {
        this.students = students;
        this.cntx = cntx;
        mDatabases = new DbHelper(cntx);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public TextView mUob;
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
    public EditGroupStudentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_student_custum_layout, parent, false);
        return new EditGroupStudentsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EditGroupStudentsAdapter.MyViewHolder holder, final int position) {

        final Student student = students.get(position);
        holder.mName.setText(student.getStdName());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // students.remove(position);
                EditGroup.mGroupStudents.remove(position);
                EditGroup.editGroupStudentAdapter.notifyDataSetChanged();
                updatData(student.getUob());
            }
        });

    }
    private void updatData(String uob) {
        SQLiteDatabase db=cntx.openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM STUDENT_TABLE where UOB = '"+uob+"'", null);
        int id=0;
        while (c.moveToNext()){
            id= Integer.parseInt(c.getString(3));

        }
        db.execSQL("update STUDENT_TABLE set GROUP_ID='"+0+"' where uob='"+uob+"'");
        Cursor cc=db.rawQuery("SELECT * FROM GROUP_NAM where G_ID = '"+id+"'", null);
        int num=0;
        while (cc.moveToNext()){
            num= Integer.parseInt(cc.getString(3));

        }
        num=num-1;
        db.execSQL("update GROUP_NAM set NO_STUDENT='"+num+"' where G_ID='"+id+"'");
        Toast.makeText(cntx, "Data Updated Succesfuly", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return students.size();
    }
}

