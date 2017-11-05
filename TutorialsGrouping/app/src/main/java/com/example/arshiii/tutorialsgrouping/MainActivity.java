package  com.example.arshiii.tutorialsgrouping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;

    public static final String PREFERENCEDATA = "DATA" ;
    SharedPreferences sharedpreferences;
    private DbHelper mDatabase;
    private ArrayList<GroupData> mGroupData;
    private RecyclerView mGroupRecyclearView;
    private GroupAdapter mGroupAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.main_page_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tutorials");
        mDatabase = new DbHelper(this);
        mGroupData = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allStudentIntent = new Intent(MainActivity.this,All_Students.class);
                allStudentIntent.putExtra("className","MainActivity");
                startActivity(allStudentIntent);
               // ActivityCompat.startActivityForResult(MainActivity.this, new Intent(MainActivity.this, All_Students.class), 0, null);

            }
        });
        final FloatingActionButton fabPrintGroup = (FloatingActionButton) findViewById(R.id.fabPrintGroup);
        fabPrintGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGroupData = mDatabase.getGroupData();
                if(mGroupData.size()>0){
                    Document document=new Document();
                    Random random=new Random();
                    int i=  random.nextInt();
                    String path="/GroupList'"+i+"'.pdf";
//output file path
                    String filepath= Environment.getExternalStorageDirectory()+path;
                    try {
//create pdf writer instance
                        PdfWriter.getInstance(document, new FileOutputStream(filepath));
//open the document for writing
                        document.open();
//add paragraph to the document
                        document.add(new Paragraph("Group Data" +"\n\n\n\n"));
                        for(int j=0;j<mGroupData.size();j++){
                            document.add(new Paragraph("Sr. No: "+ j +"\n"));
                            document.add(new Paragraph("Name: "+ mGroupData.get(j).getgName() +"\n"));
                            document.add(new Paragraph("No of Students: "+ mGroupData.get(j).getgCount() +"\n"));
                            if(mGroupData.get(j).getgId().equals("0")){
                                document.add(new Paragraph("Teacher: "+ "No Teacher" +"\n"));
                            }
                            else{
                                String groupName=getGroupName(mGroupData.get(j).getgTid());
                                document.add(new Paragraph("Teacher: "+groupName+"\n"));
                            }

                            document.add(new Paragraph("--------------" +"\n\n\n\n"));
                            // document.add(new Paragraph( +"\n"));

                        }
                        fabPrintGroup.setEnabled(false);
//close the document
                        document.close();
                        Toast.makeText(MainActivity.this, "Document Created Succesfuly", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (DocumentException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "No data to Print", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setValues();
        disPlayGroup();



    }

    private String getGroupName(String id) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM TEACHER_TABLE where KEY_ID='"+id+"'", null);
        while (c.moveToNext()){
            return  c.getString(2);
        }
        return "";
    }
    public void disPlayGroup(){
        mGroupData = mDatabase.getGroupData();




        if(mGroupData.size()>0) {

            mGroupRecyclearView = (RecyclerView) findViewById(R.id.startPage_recyclearView);
            mGroupAdapter = new GroupAdapter(mGroupData,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mGroupRecyclearView.setLayoutManager(mLayoutManager);
            mGroupRecyclearView.setItemAnimator(new DefaultItemAnimator());
            mGroupRecyclearView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mGroupRecyclearView.setAdapter(mGroupAdapter);
            mGroupAdapter.notifyDataSetChanged();
            mGroupRecyclearView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mGroupRecyclearView, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String groupId = mGroupData.get(position).getgId();
                            String groupName = mGroupData.get(position).getgName();
                            String teacherId = mGroupData.get(position).getgTid();
                            Intent indiviual_group_activity = new Intent (MainActivity.this,Group_View.class);
                            indiviual_group_activity.putExtra("Group_Id",groupId);
                            indiviual_group_activity.putExtra("Group_name",groupName);
                            indiviual_group_activity.putExtra("tId",teacherId);
                            startActivity(indiviual_group_activity);
                        }

                        @Override
                        public void onLongClick(View view, final int position) {


                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setMessage("Do You want to Delete "+ mGroupData.get(position).getgName());
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            mDatabase.deletGroup(mGroupData.get(position).getgId());
                                            updatTeacherData(mGroupData.get(position).getgTid());
                                            SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
                                            Cursor c=db.rawQuery("SELECT * FROM STUDENT_TABLE where GROUP_ID='"+mGroupData.get(position).getgId()+"'", null);
                                            while (c.moveToNext())
                                            {
                                                String id=c.getString(0);
                                                db.execSQL("update STUDENT_TABLE set GROUP_ID = 0 where uob='"+id+"'");
                                                Toast.makeText(MainActivity.this, "Group Deleted", Toast.LENGTH_SHORT).show();


                                            }
                                            mGroupData.remove(position);
                                            mGroupAdapter.notifyDataSetChanged();


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
            Toast.makeText(this, "No Group to show", Toast.LENGTH_SHORT).show();
        }

    }

    private void updatTeacherData(String tId) {
        SQLiteDatabase db=openOrCreateDatabase("TUTORIAL_GROUPS", MODE_PRIVATE, null);
        db.execSQL("update TEACHER_TABLE set GROUP_NAME='"+0+"' where KEY_ID='"+tId+"'");
    }


    private void setValues() {
        sharedpreferences = getSharedPreferences(PREFERENCEDATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("GROUP", "");
        editor.putString("TEACHER","");
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
           /* case R.id.main_all_students:
                Intent allStudentIntent = new Intent(MainActivity.this,All_Students.class);
                startActivity(allStudentIntent);
                break;*/
            case R.id.main_new_group_create_btn:
                Intent newGroupIntent = new Intent(MainActivity.this,CreateGroup.class);
                newGroupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newGroupIntent);
                break;
            case R.id.main_new_student_create_btn:
                Intent newStudentIntent = new Intent(MainActivity.this,AddStudent.class);
                newStudentIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newStudentIntent);

                break;
            case R.id.add_new_teacher:
                Intent newTeacherIntent = new Intent(MainActivity.this,AddTeacher.class);
                newTeacherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newTeacherIntent);
               // finish();
                break;
            case R.id.main_all_teachers:
                Intent allTeachersIntent = new Intent(MainActivity.this,AllTeachers.class);
                allTeachersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(allTeachersIntent);
              //  finish();
                break;
            case R.id.uploadStudents:
                Intent allStudentsIntent = new Intent(MainActivity.this,FileUploading.class);
                allStudentsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(allStudentsIntent);
                //  finish();
                break;
            default:
                //Do nothing
        }

        return true;
    }


}
