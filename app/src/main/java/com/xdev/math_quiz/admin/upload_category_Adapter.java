package com.xdev.math_quiz.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xdev.math_quiz.R;

import java.util.List;
import java.util.Map;

public class upload_category_Adapter extends RecyclerView.Adapter<upload_category_Adapter.Viewholder> {

    List<categroy_model> cat_list;
    public  upload_category_Adapter(List<categroy_model>catlist){
        this.cat_list=catlist;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_row_design,parent,false);
        return new  Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String title = cat_list.get(position).getName();
        holder.setData(title,position,this);

    }



    @Override
    public int getItemCount() {
        return cat_list.size();
    }
    public class Viewholder extends  RecyclerView.ViewHolder{
        private TextView catName;
        private ImageView deleteB;
        private Dialog loadingDialog;
        private Dialog editDialog;
        private EditText tv_editCatName;
        private Button updateCatB;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.catName);
            deleteB = itemView.findViewById(R.id.catDelB);

            loadingDialog = new Dialog(itemView.getContext());
            loadingDialog.setContentView(R.layout.loading_progressbar);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            editDialog = new Dialog(itemView.getContext());
            editDialog.setContentView(R.layout.edit_category_dialog);
            editDialog.setCancelable(true);
            editDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            tv_editCatName = editDialog.findViewById(R.id.ec_cat_name);
            updateCatB = editDialog.findViewById(R.id.ec_add_btn);

        }
        private void setData(String title, final int pos, final upload_category_Adapter adapter)
        {
            catName.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    admin_quiz_activity.selected_cat_index = pos;


                    Intent intent = new Intent(itemView.getContext(), SetsActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    tv_editCatName.setText(cat_list.get(pos).getName());
                    editDialog.show();

                    return false;
                }
            });

            updateCatB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(tv_editCatName.getText().toString().isEmpty())
                    {
                        tv_editCatName.setError("Enter Category Name");
                        return;
                    }

                    updateCategory(tv_editCatName.getText().toString(), pos, itemView.getContext(), adapter);

                }
            });

            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog dialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Delete Category")
                            .setMessage("Do you want to delete this category ?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    deleteCategory(pos, itemView.getContext(), adapter);
                                }
                            })
                            .setNegativeButton("Cancel",null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(Color.RED);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setBackgroundColor(Color.RED);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,0,50,0);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setLayoutParams(params);

                }
            });

        }

        private void deleteCategory(final int id, final Context context, final upload_category_Adapter adapter)
        {
            loadingDialog.show();

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();


            Map<String,Object> catDoc = new ArrayMap<>();
            int index=1;
            for(int i=0; i < cat_list.size(); i++)
            {
                if( i != id)
                {
                    catDoc.put("CAT" + String.valueOf(index) + "_ID", cat_list.get(i).getId());
                    catDoc.put("CAT" + String.valueOf(index) + "_NAME", cat_list.get(i).getName());
                    index++;
                }

            }

            catDoc.put("COUNT", index - 1);

            firestore.collection("QUIZ").document("Categories")
                    .set(catDoc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(context,"Category deleted successfully",Toast.LENGTH_SHORT).show();

                            admin_quiz_activity.catList.remove(id);

                            adapter.notifyDataSetChanged();

                            loadingDialog.dismiss();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }
                    });


        }

        private void updateCategory(final String catNewName, final int pos, final Context context, final upload_category_Adapter adapter)
        {
            editDialog.dismiss();

            loadingDialog.show();

            Map<String,Object> catData = new ArrayMap<>();
            catData.put("NAME",catNewName);

            final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            firestore.collection("QUIZ").document(cat_list.get(pos).getId())
                    .update(catData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Map<String,Object> catDoc = new ArrayMap<>();
                            catDoc.put("CAT" + String.valueOf(pos + 1) + "_NAME",catNewName);

                            firestore.collection("QUIZ").document("Categories")
                                    .update(catDoc)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(context,"Category Name Changed Successfully",Toast.LENGTH_SHORT).show();
                                            admin_quiz_activity.catList.get(pos).setName(catNewName);
                                            adapter.notifyDataSetChanged();

                                            loadingDialog.dismiss();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                                            loadingDialog.dismiss();
                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }
                    });


        }

    }
}

