package com.xdev.math_quiz.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xdev.math_quiz.R;
import com.xdev.math_quiz.admin.admin_quiz_activity;

public class MainActivity extends AppCompatActivity {

    TextView phonenologin1, phonelogin3, adminlogin;
    ImageView phonelogin2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenologin1 = findViewById(R.id.signwithphone1);
        phonelogin2 = findViewById(R.id.signwithphoneno2);
        phonelogin3 = findViewById(R.id.signwithphone3);
        adminlogin = findViewById(R.id.adminlogin);


        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,admin_quiz_activity.class);
                startActivity(i);
            }
        });

        phonenologin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Otp_auth.class);
                startActivity(i);
            }
        });
        phonelogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Otp_auth.class);
                startActivity(i);

            }
        });
        phonelogin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Otp_auth.class);
                startActivity(i);

            }
        });


    }









}