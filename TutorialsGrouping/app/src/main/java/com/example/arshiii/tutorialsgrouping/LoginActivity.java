package com.example.arshiii.tutorialsgrouping;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolBAR;
    private Button login_btn;
    private EditText login_username;
    private EditText login_password;
    private SQLiteDatabase db;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolBAR = (Toolbar) findViewById(R.id.login_toolbar);
        login_btn= (Button) findViewById(R.id.login_btn);
        login_username= (EditText) findViewById(R.id.login_username);
        login_password= (EditText) findViewById(R.id.login_password);
        db=openOrCreateDatabase("TUTORIAL_GROUPS", Context.MODE_PRIVATE, null);
        String userTable="CREATE TABLE IF NOT EXISTS users(username VARCHAR PRIMARY KEY UNIQUE,password VARCHAR);";
        db.execSQL(userTable);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c=db.rawQuery("SELECT * FROM users", null);
                if(c.getCount()==0)
                {
                    String values="INSERT INTO users VALUES ('admin','1234');";
                    db.execSQL(values);
                    c=db.rawQuery("SELECT * FROM users", null);

                    return;
                }

                String user=null;
                String pass=null;
                if(c.moveToNext()){
                    user=c.getString(0);
                    pass=c.getString(1);

                }
                if(login_username.getText().toString().equals(user) && login_password.getText().toString().equals(pass)){
                    Toast.makeText(LoginActivity.this, "Login Succesful..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setSupportActionBar(mToolBAR);
        getSupportActionBar().setTitle("Login");
    }
}
