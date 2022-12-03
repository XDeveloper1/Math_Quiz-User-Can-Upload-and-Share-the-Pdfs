package com.xdev.math_quiz.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xdev.math_quiz.R;
import com.xdev.math_quiz.home.set_em_model;

import java.util.List;

public class cat_grid_adapter extends RecyclerView.Adapter<cat_grid_adapter.Viewholer> {

    List<set_em_model> cat_list;

    public cat_grid_adapter(List<set_em_model> catlist) {
        this.cat_list = catlist;
    }

    @NonNull
    @Override
    public Viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_item_layout, parent, false);

        return new Viewholer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholer holder, int position) {
        String fname = cat_list.get(position).getName();
        String id = cat_list.get(position).getId();
        holder.setData(fname,id, position, this);

    }


    @Override
    public int getItemCount() {
        return cat_list.size();
    }

    public class Viewholer extends RecyclerView.ViewHolder {
        TextView catName;
        Dialog loadingDialog;


        public Viewholer(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.cate_Name);
            loadingDialog = new Dialog(itemView.getContext());
            loadingDialog.setContentView(R.layout.loading_progressbar);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }


        public void setData(String fname, String s , int position, cat_grid_adapter adapter) {
            catName.setText(fname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dashboard_frag.selected_cate_index = position;
                    Intent intent = new Intent(itemView.getContext(), question_set.class);
                    intent.putExtra("category_name",fname);
                    intent.putExtra("category_id",s);
                    itemView.getContext().startActivity(intent);
                }
            });
        }


    }
}

