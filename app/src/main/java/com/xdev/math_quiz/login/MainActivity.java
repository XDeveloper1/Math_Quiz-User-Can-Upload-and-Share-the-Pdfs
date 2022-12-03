package com.xdev.math_quiz.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.admin.admin_quiz_activity;
import com.xdev.math_quiz.home.Home_page;

public class MainActivity extends AppCompatActivity {

    TextView loginwithgoogle1, loginwithgoogle3, phonenologin1, phonelogin3, adminlogin;
    ImageView loginwithgoogle2, phonelogin2;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginwithgoogle2 = findViewById(R.id.signwithgoogle2);
        loginwithgoogle1 = findViewById(R.id.signwithgoogle1);
        loginwithgoogle3 = findViewById(R.id.signwithgoogle3);
        phonenologin1 = findViewById(R.id.signwithphone1);
        phonelogin2 = findViewById(R.id.signwithphoneno2);
        phonelogin3 = findViewById(R.id.signwithphone3);
        adminlogin = findViewById(R.id.adminlogin);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
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


        loginwithgoogle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressddialogm();

                signin();
            }
        });

        loginwithgoogle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressddialogm();
                signin();
            }
        });
        loginwithgoogle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressddialogm();
                signin();
            }
        });

    }


    private void progressddialogm() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading...");
        dialog.setTitle("Please Wait..");
        dialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
        dialog.show();
    }

    private void signin() {

        Intent signinIntent = gsc.getSignInIntent();

        startActivityForResult(signinIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                naviagtetosecandactiviy();
            } catch (ApiException e) {
                dialog.dismiss();
                Toast.makeText(this, "Please select email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void naviagtetosecandactiviy() {
        finish();
        Intent intent = new Intent(MainActivity.this, Home_page.class);
        startActivity(intent);
    }
}