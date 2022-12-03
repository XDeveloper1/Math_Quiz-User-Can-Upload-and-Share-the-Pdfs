package com.xdev.math_quiz.dashboard;

import static com.xdev.math_quiz.dashboard.Dashboard_frag.catlist;
import static com.xdev.math_quiz.dashboard.Dashboard_frag.selected_cate_index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.admin.categroy_model;

import java.util.ArrayList;
import java.util.List;

public class question_set extends AppCompatActivity {
    RecyclerView catGrid;
    public static List<categroy_model> fcatlist = new ArrayList<>();
    FirebaseFirestore firestore;
    public static String name;
    public static String id;

    public static List<String> setsIDss = new ArrayList<>();
    public static int selected_sets_index = 0;
    set_emp_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set);
        catGrid = findViewById(R.id.catgrid);
        firestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        name = i.getStringExtra("category_name");
        id = i.getStringExtra("category_id");
        Log.d(name, "onCreate: ");
        Log.d(id, "onCreate: ");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        catGrid.setLayoutManager(layoutManager);

        loadSets();


    }


    private void loadSets() {

        setsIDss.clear();


        firestore.collection("QUIZ").document(catlist.get(selected_cate_index).getId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        long noOfSets = (long) documentSnapshot.get("SETS");

                        for (int i = 1; i <= noOfSets; i++) {
                            setsIDss.add(documentSnapshot.getString("SET" + String.valueOf(i) + "_ID"));
                        }

                        catlist.get(selected_cate_index).setSetCounter(documentSnapshot.getString("COUNTER"));
                        catlist.get(selected_cate_index).setNoOfSets(String.valueOf(noOfSets));

                        adapter = new set_emp_adapter(setsIDss);
                        catGrid.setAdapter(adapter);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(question_set.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}

