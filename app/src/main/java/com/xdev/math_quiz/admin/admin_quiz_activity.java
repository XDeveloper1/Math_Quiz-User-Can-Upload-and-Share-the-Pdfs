package com.xdev.math_quiz.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xdev.math_quiz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class admin_quiz_activity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    private RecyclerView cat_recycler_view;
    Button addCatB;
    public static List<categroy_model> catList = new ArrayList<>();
    public static int selected_cat_index = 0;

    FirebaseFirestore firestore;
    Dialog loadingDialog, addCatDialog;
    String dialogCatName;
    Button dialogAddB;
    private upload_category_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_quiz);
        getSupportActionBar().setTitle("Categories");
        cat_recycler_view = findViewById(R.id.cat_recycler);
        addCatB = findViewById(R.id.addCatB);
        loadingDialog = new Dialog(admin_quiz_activity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        firestore = FirebaseFirestore.getInstance();


        addCatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cat_recycler_view.setLayoutManager(layoutManager);

        loadData();
    }

    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "add category name");
    }


    private void loadData() {
        loadingDialog.show();

        catList.clear();

        firestore.collection("QUIZ").document("Categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();

                            if (doc.exists()) {
                                long count = (long) doc.get("COUNT");

                                for (int i = 1; i <= count; i++) {
                                    String catName = doc.getString("CAT" + String.valueOf(i) + "_NAME");
                                    String catid = doc.getString("CAT" + String.valueOf(i) + "_ID");

                                    catList.add(new categroy_model(catid, catName, "0", "1"));
                                }

                                adapter = new upload_category_Adapter(catList);
                                cat_recycler_view.setAdapter(adapter);

                            } else {
                                Toast.makeText(admin_quiz_activity.this, "No Category Document Exists!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } else {

                            Toast.makeText(admin_quiz_activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        loadingDialog.dismiss();
                    }
                });

    }

    //
    private void addNewCategory(final String title) {
        System.out.println(title);
        loadingDialog.show();

        final Map<String, Object> catData = new ArrayMap<>();
        catData.put("NAME", title);
        catData.put("SETS", 0);
        catData.put("COUNTER", "1");

        final String doc_id = firestore.collection("QUIZ").document().getId();

        firestore.collection("QUIZ").document(doc_id)
                .set(catData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Map<String, Object> catDoc = new ArrayMap<>();
                        catDoc.put("CAT" + String.valueOf(catList.size() + 1) + "_NAME", title);
                        catDoc.put("CAT" + String.valueOf(catList.size() + 1) + "_ID", doc_id);
                        catDoc.put("COUNT", catList.size() + 1);

                        firestore.collection("QUIZ").document("Categories")
                                .update(catDoc)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(admin_quiz_activity.this, "Category added successfully", Toast.LENGTH_SHORT).show();

                                        catList.add(new categroy_model(doc_id, title, "0", "1"));

                                        adapter.notifyItemInserted(catList.size());

                                        loadingDialog.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(admin_quiz_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        loadingDialog.dismiss();
                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_quiz_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });


    }

    @Override
    public void applyTexts(String username) {
        System.out.println(username);
        dialogCatName = username;
        addNewCategory(dialogCatName);
    }
}
