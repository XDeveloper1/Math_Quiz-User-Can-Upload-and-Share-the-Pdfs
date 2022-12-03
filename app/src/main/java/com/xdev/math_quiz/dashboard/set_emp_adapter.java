package com.xdev.math_quiz.dashboard;
import static com.xdev.math_quiz.dashboard.question_set.selected_sets_index;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xdev.math_quiz.R;

import java.util.List;

public class set_emp_adapter extends RecyclerView.Adapter<set_emp_adapter.ViewHolder> {

    private List<String> setIDs;

    public set_emp_adapter(List<String> setIDs) {
        this.setIDs = setIDs;
    }


    @NonNull
    @Override
    public set_emp_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_num, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull set_emp_adapter.ViewHolder viewHolder, int i) {

        String setID = setIDs.get(i);
        viewHolder.setData(i, setID, this);
    }

    @Override
    public int getItemCount() {
        return setIDs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView setName;

        private Dialog loadingDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            setName = itemView.findViewById(R.id.set_name);


            loadingDialog = new Dialog(itemView.getContext());
            loadingDialog.setContentView(R.layout.loading_progressbar);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        private void setData(final int pos, final String setID, final set_emp_adapter adapter) {
            setName.setText("" + String.valueOf(pos + 1));


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selected_sets_index = pos;
                    String data = String.valueOf(pos);


                    Log.d(data, "onClick: asd");
                    Intent i = new Intent(itemView.getContext(), Question_Activity.class);
                    i.putExtra("setno", pos + 1);
                    itemView.getContext().startActivity(i);
                }
            });


        }
    }
}



