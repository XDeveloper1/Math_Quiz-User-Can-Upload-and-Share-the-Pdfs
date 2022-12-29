package com.xdev.math_quiz.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.login.MainActivity;


public class Account_Frag extends Fragment {
    FirebaseDatabase db;
    TextView email;
    TextView logout;
    TextView uname;
    TextView phone;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_account_, viewGroup, false);

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("users");
        uname = view.findViewById(R.id.username);
        email = view.findViewById(R.id.emails);
        phone = view.findViewById(R.id.pnumber);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        gettingdataandseeting();


        return view;
    }

    private void gettingdataandseeting() {
        reference.child("numbers").child("8791805044").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String unames = snapshot.child("uname").getValue().toString();
                    String phonenumbers = snapshot.child("fno").getValue().toString();
                    String emailss = snapshot.child("uname").getValue().toString();
                    uname.setText(unames);
                    phone.setText(phonenumbers);
                    email.setText(unames + phonenumbers + "@gmail.com");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}