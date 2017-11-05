package com.example.arshiii.tutorialsgrouping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Select_Group extends AppCompatActivity {

    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__group);

        mToolBar = (Toolbar) findViewById(R.id.select_group_app_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Select Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
