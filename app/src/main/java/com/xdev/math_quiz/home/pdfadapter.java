package com.xdev.math_quiz.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.xdev.math_quiz.R;

public class pdfadapter extends FirebaseRecyclerAdapter<pdf_model, pdfadapter.myviewholder> {


    public pdfadapter(FirebaseRecyclerOptions<pdf_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(final myviewholder holder, int position, final pdf_model model) {
        holder.pbar.setVisibility(View.VISIBLE);
        holder.pbar.setEnabled(true);
        holder.header.setText(model.getFilename());
        holder.pbar.setVisibility(View.INVISIBLE);
        holder.pbar.setEnabled(false);

        holder.img1.setOnClickListener(view -> {
            Intent intent = new Intent(holder.img1.getContext(), viewpdf.class);

            intent.putExtra("filename", model.getFilename());
            intent.putExtra("url", model.getUrl());


            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            holder.img1.getContext().startActivity(intent);
        });

    }


    @Override
    public pdfadapter.myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign, parent, false);

        return new myviewholder(view);
    }

    public static class myviewholder extends RecyclerView.ViewHolder {

        ProgressBar pbar;
        ImageView img1;
        TextView header;


        public myviewholder(View itemView) {
            super(itemView);
            pbar = itemView.findViewById(R.id.singlerow_progressbar);
            img1 = itemView.findViewById(R.id.img1);
            header = itemView.findViewById(R.id.header);


        }
    }
}
