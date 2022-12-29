package com.xdev.math_quiz.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.home.Home_page;

import java.util.concurrent.TimeUnit;

public class Otp_auth extends AppCompatActivity {
        EditText phone_number,otp;
        TextView editphno;
        Button b1,b2;
        String number;
        String otpid;
        FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_auth);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        editphno = findViewById(R.id.editphonenumber);


        phone_number = findViewById(R.id.phonenumber);
        otp = findViewById(R.id.otp);
        b1 = findViewById(R.id.send_button);
        b2 = findViewById(R.id.otp_login);
        number = phone_number.getText().toString();
        editphno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number.setEnabled(true);
                b1.setEnabled(true);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intiateotp();



            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().length() != 6) {

                    Toast.makeText(Otp_auth.this, "invalid otp", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });
    }

    private void intiateotp() {
      String numbers = phone_number.getText().toString();
      String fno ="+91"+numbers;
      if (numbers!=null){
          phone_number.setEnabled(false);
          b1.setEnabled(false);
          Toast.makeText(this, "OTP SENT", Toast.LENGTH_SHORT).show();
      }else{
          phone_number.setError("please enter phone number");

      }
        Log.d(numbers, "intiateotp: ");
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(fno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks()

                        {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid =s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.d(e.getMessage(), "onVerificationFailed: ");


                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        b2.setEnabled(false);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String number = phone_number.getText().toString();
                            Intent intent = new Intent(Otp_auth.this, Home_page.class);
                            intent.putExtra("number", number);
                            startActivity(intent);
                        } else {
                            b2.setEnabled(true);
                            Toast.makeText(Otp_auth.this, "sign in error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}