package com.xdev.math_quiz.dashboard;

import android.app.Dialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.home.set_em_model;

import java.util.ArrayList;
import java.util.List;


public class Dashboard_frag extends Fragment {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private Dialog loadingDialog;
    public static List<set_em_model> catlist = new ArrayList<>();
    ProgressBar pbar;
    cat_grid_adapter adapter;
    private FirebaseFirestore firestore;
    public static int selected_cate_index = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_frag, container, false);
        firestore = FirebaseFirestore.getInstance();

        loadingDialog = new Dialog(getActivity());
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView = view.findViewById(R.id.frecview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        pbar =view.findViewById(R.id.quiz_progressbar);
        loadData();
        return view;
    }

    private void loadData() {
        loadingDialog.show();

        catlist.clear();

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

                                    catlist.add(new set_em_model(catid,catName, "0", "1"));
                                }

                                adapter = new cat_grid_adapter(catlist);
                                recyclerView.setAdapter(adapter);

                            } else {
                                Toast.makeText(getActivity(), "No Category Document Exists!", Toast.LENGTH_SHORT).show();
//                                finish();
                            }

                        } else {

                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        loadingDialog.dismiss();
                    }
                });

    }

}