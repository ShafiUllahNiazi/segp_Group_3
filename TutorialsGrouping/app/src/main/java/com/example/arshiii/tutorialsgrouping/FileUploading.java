package com.example.arshiii.tutorialsgrouping;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;

public class FileUploading extends AppCompatActivity implements View.OnClickListener {

    Button bUpload;
    private DbHelper mDatabase;
    //String path;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath;
    //TextView tvFileName;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_uploading);
        mDatabase = new DbHelper(this);
        // tvFileName = (TextView) findViewById(R.id.tv_file_name);
        bUpload = (Button) findViewById(R.id.b_upload);
        bUpload.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v== bUpload){

            //on attachment icon click
            showFileChooser();
        }


    }
    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }

                Boolean check=false;
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);
                Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(csv))$)");
                Matcher mtch = fileExtnPtrn.matcher(selectedFilePath);
                if(mtch.matches()){
                    check=true;
                }
                else{
                    check=false;
                }
                //Toast.makeText(this, check+selectedFilePath, Toast.LENGTH_SHORT).show();

                if(selectedFilePath != null && !selectedFilePath.equals("")&& check==true){
                    //tvFileName.setText(selectedFilePath);
                    //path=tvFileName.getText().toString();
                    List<String[]> list = new ArrayList<String[]>();
                    String next[] = {};
                    try {
                        //InputStream inputStream = openFileInput("C:/Users/Muhammad/Desktop//usman.csv");
                        //File path = new File("C://Users//Muhammad//Desktop//usman.csv");
                        //InputStreamReader csvStreamReader = new InputStreamReader(inputStream);
                        File files=new File(selectedFilePath);
                        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(files)));
                        //CSVReader reader = new CSVReader(csvStreamReader);
                        for (;;) {
                            next = reader.readNext();
                            if (next != null) {
                                list.add(next);
                            } else {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i=0;i<list.size();i++){
                        try{
                            mDatabase.insertStudent(list.get(i)[0], list.get(i)[1], list.get(i)[2], "0");
                        }catch(Exception e){
                            Toast.makeText(this, "Some Error Ocuur", Toast.LENGTH_SHORT).show();

                        }

                       // Toast.makeText(this,list.get(i)[0]+" "+list.get(i)[1]+" "+list.get(i)[2]+" "+list.get(i)[3],Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this,"Cannot upload this  file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
