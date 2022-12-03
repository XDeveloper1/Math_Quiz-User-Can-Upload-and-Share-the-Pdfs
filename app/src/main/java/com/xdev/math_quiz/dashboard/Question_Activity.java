package com.xdev.math_quiz.dashboard;

import static com.xdev.math_quiz.dashboard.Dashboard_frag.catlist;
import static com.xdev.math_quiz.dashboard.Dashboard_frag.selected_cate_index;
import static com.xdev.math_quiz.dashboard.question_set.selected_sets_index;
import static com.xdev.math_quiz.dashboard.question_set.setsIDss;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.home.Quiz_model;
import com.xdev.math_quiz.home.set_em_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Question_Activity extends AppCompatActivity implements View.OnClickListener {
    private TextView question, qcount, timer;
    private Button option1, option2, option3, option4;
    public static List<Quiz_model> QuestionList = new ArrayList<>();

    public static List<set_em_model> catList = new ArrayList<>();
    int questnum;
    Dialog loadingDialog;
    private CountDownTimer countDownTimer;
    int score;
    String setno;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        loadingDialog = new Dialog(Question_Activity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        question = findViewById(R.id.question_quiz);
        qcount = findViewById(R.id.question_no_quiz);
        timer = findViewById(R.id.countdown);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        setno = getIntent().getStringExtra("setno");
        firestore = FirebaseFirestore.getInstance();


        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        getQuestionlist();
        score = 0;


    }

    private void getQuestionlist() {
        QuestionList.clear();

        loadingDialog.show();
        firestore.collection("QUIZ").document(catlist.get(selected_cate_index).getId())
                .collection(setsIDss.get(selected_sets_index)).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            docList.put(doc.getId(), doc);
                        }

                        QueryDocumentSnapshot quesListDoc = docList.get("QUESTIONS_LIST");

                        String count = quesListDoc.getString("COUNT");

                        for (int i = 0; i < Integer.valueOf(count); i++) {
                            String quesID = quesListDoc.getString("Q" + String.valueOf(i + 1) + "_ID");

                            QueryDocumentSnapshot quesDoc = docList.get(quesID);

                            QuestionList.add(new Quiz_model(
                                    quesID,
                                    quesDoc.getString("QUESTION"),
                                    quesDoc.getString("A"),
                                    quesDoc.getString("B"),
                                    quesDoc.getString("C"),
                                    quesDoc.getString("D"),
                                    Integer.valueOf(quesDoc.getString("ANSWER"))
                            ));

                        }
                        setQuestion();

//                        adapter = new QuestionAdapter(quesList);
//                        quesView.setAdapter(adapter);

                        loadingDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Question_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });

    }


    private void setQuestion() {
        timer.setText(String.valueOf(120));
        question.setText(QuestionList.get(0).getQuestion());
        option1.setText(QuestionList.get(0).getOptionA());
        option2.setText(QuestionList.get(0).getOptionB());
        option3.setText(QuestionList.get(0).getOptionC());
        option4.setText(QuestionList.get(0).getOptionD());

        qcount.setText(String.valueOf(1) + "/" + String.valueOf(QuestionList.size()));
        Starttimer();
        questnum = 0;
    }

    private void Starttimer() {
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long l) {
                if (l < 120000)
                    timer.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                changequestion();

            }
        };
        countDownTimer.start();
    }

    private void changequestion() {

        if (questnum < QuestionList.size() - 1) {
            questnum++;


            playAnim(question, 0, 0);
            playAnim(option1, 0, 1);
            playAnim(option2, 0, 2);
            playAnim(option3, 0, 3);
            playAnim(option4, 0, 4);

            qcount.setText(String.valueOf(questnum + 1) + "/" + String.valueOf(QuestionList.size()));

            timer.setText(String.valueOf(120));
            Starttimer();
            enablebutton();


        } else {
//            go to score Activity
            Intent intent = new Intent(this, Score_Activity.class);
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(QuestionList.size()));
            startActivity(intent);
            Question_Activity.this.finish();

        }
    }

    private void playAnim(View view, final int value, int viewnum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {


                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (value == 0) {
                            switch (viewnum) {
                                case 0:
                                    ((TextView) view).setText(QuestionList.get(questnum).getQuestion());
                                    break;
                                case 1:
                                    ((Button) view).setText(QuestionList.get(questnum).getOptionA());
                                    break;
                                case 2:
                                    ((Button) view).setText(QuestionList.get(questnum).getOptionB());
                                    break;
                                case 3:
                                    ((Button) view).setText(QuestionList.get(questnum).getOptionC());
                                    break;
                                case 4:
                                    ((Button) view).setText(QuestionList.get(questnum).getOptionD());
                                    break;
                            }
                            if (viewnum != 0)
                                ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));

                            playAnim(view, 1, viewnum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });


    }

    @Override
    public void onClick(View view) {
        int selectedOption = 0;
        switch (view.getId()) {
            case R.id.option1:
                selectedOption = 1;
                break;

            case R.id.option2:
                selectedOption = 2;
                break;

            case R.id.option3:
                selectedOption = 3;
                break;

            case R.id.option4:
                selectedOption = 4;
                break;

            default:

        }
        countDownTimer.cancel();
        checkanswer(selectedOption, view);

    }

    private void checkanswer(int selectedOption, View view) {
        if (selectedOption == QuestionList.get(questnum).getCorrectAns()) {

//            RightAnswer
            disableotions();
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));


            score++;
        } else {
//            wrong Answer
            disableotions();
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));


            switch (QuestionList.get(questnum).getCorrectAns()) {
                case 1:
                    disableotions();
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                    break;
                case 2:
                    disableotions();
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                    break;
                case 3:
                    disableotions();
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                    break;
                case 4:
                    disableotions();
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                    break;

            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                changequestion();

            }
        }, 100);


    }

    private void enablebutton() {

        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
    }

    private void disableotions() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);


    }

}
