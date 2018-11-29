package com.dushyant.loginapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dushyant.loginapplication.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        System.out.println("Action: " + action);
        System.out.println("Data: " + (data != null ? data.getPath(): "null"));
    }


}
