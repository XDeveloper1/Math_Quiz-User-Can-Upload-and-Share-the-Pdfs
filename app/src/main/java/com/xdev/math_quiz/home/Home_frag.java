package com.xdev.math_quiz.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xdev.math_quiz.R;


public class Home_frag extends Fragment {
    pdfadapter adapter;
    FirebaseDatabase db;
    LinearLayoutManager linearLayoutManager;
    ProgressBar pbar;
    RecyclerView recview;
    DatabaseReference reference;
    String subscription;
    private Object home;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_home_frag, viewGroup, false);

        recview = view.findViewById(R.id.rcviwer);
        pbar = view.findViewById(R.id.Pbar);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("users");
        checkexitance();

        return view;
    }

    private void showdata() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recview.setItemAnimator(null);
        recview.setLayoutManager(new LinearLayoutManager(getActivity()));
        pbar.setVisibility(View.VISIBLE);
        pbar.setEnabled(true);
        System.out.println(subscription + "checks");
        FirebaseRecyclerOptions<pdf_model> options = new FirebaseRecyclerOptions.Builder<pdf_model>().setQuery(FirebaseDatabase.getInstance().getReference(subscription), pdf_model.class).build();
        adapter = new pdfadapter(options);
        recview.setAdapter(adapter);
        pbar.setVisibility(View.INVISIBLE);
        pbar.setEnabled(false);
        adapter.notifyDataSetChanged();

    }

    private void checkexitance() {
        String number = getArguments().getString("number");
        reference.child("numbers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String chek = String.valueOf(snapshot.child(number).exists());
                    if (chek.matches("true")) {
                        checkuser();
                    } else {
                        addinguser();
                        checkuser();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkuser() {

        String number = getArguments().getString("number");
        if (number != null) {
            reference.child("numbers").child(number).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {


                        String subs = snapshot.child("subscription").getValue().toString();

                        if (subs.matches("paid")) {
                            subscription = "quizpdfs";
                            showdata();
                            adapter.startListening();


                        } else {
                            if (subs.matches("unpaid")) {
                                subscription = "demopdfs";
                                showdata();
                                adapter.startListening();


                            }

                        }


                    } else {
                        System.out.println("do nothing");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {

            System.out.println("something went wrong");
        }


    }

    private void addinguser() {
        String number = getArguments().getString("number");
        System.out.println(number + "aS");
        String email = "";
        String fno = number;
        String level = "0";
        String subscriptions = "unpaid";
        String uname = "demouser";
        number_model nm = new number_model(email, fno, level, subscriptions, uname);
        reference.child("numbers").child(number).setValue(nm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    checkuser();
                    Toast.makeText(getActivity(), "data Added", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActivity(), "something went worng", Toast.LENGTH_SHORT).show();
                }

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        checkuser();
//        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
//        checkuser();
//        adapter.stopListening();
    }
}