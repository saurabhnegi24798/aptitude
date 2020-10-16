package com.example.aptitude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private TextView question, numberindicator;
    private LinearLayout optionsContainer;
    private Button next;
    int score = 0;
    int pos = -1;
    int numberOfQuestions = -1;
    List<QuestionModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        question = findViewById(R.id.questions);
        numberindicator = findViewById(R.id.no_indicator);
        optionsContainer = findViewById(R.id.linearLayout2);
        final Button optionA = findViewById(R.id.button1);
        final Button optionB = findViewById(R.id.button2);
        final Button optionC = findViewById(R.id.button3);
        final Button optionD = findViewById(R.id.button4);
        next = findViewById(R.id.button6);


        final String category = getIntent().getStringExtra("Category");
        final String set = getIntent().getStringExtra("Set");

        pos = -1;
        list.clear();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("QUIZ")
                .document(category)
                .collection(set)
                .document("NumberOfQuestion")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    long NumberOfQuestion = documentSnapshot.getLong("COUNT");
                    numberOfQuestions = (int) NumberOfQuestion;

                    Log.d(String.valueOf(NumberOfQuestion), "onSuccess: ================================== ======================== ");

                    for (int i = 0; i < NumberOfQuestion; i++) {
                        db.collection("QUIZ").document(category).collection(set).
                                document("QUESTION" + String.valueOf(i + 1)).get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot docSnap) {
                                        if (docSnap.exists()) {
                                            String qus = docSnap.get("QUESTION").toString();
                                            String ans = docSnap.get("ANSWER").toString();
                                            String opA = docSnap.get("A").toString();
                                            String opB = docSnap.get("B").toString();
                                            String opC = docSnap.get("C").toString();
                                            String opD = docSnap.get("D").toString();

                                            list.add(new QuestionModel(qus, opA, opB, opC, opD, ans));

                                            if (pos == -1) {
                                                pos = 0;
                                                question.setText(list.get(pos).getQuestion());
                                                optionA.setText(list.get(pos).getOptionA());
                                                optionB.setText(list.get(pos).getOptionB());
                                                optionC.setText(list.get(pos).getOptionC());
                                                optionD.setText(list.get(pos).getOptionD());
                                                pos = 1;
                                            }
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(QuestionsActivity.this, "Check your Internet and try again !!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else
                    Toast.makeText(QuestionsActivity.this, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(QuestionsActivity.this, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
            }
        });


        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(QuestionsActivity.this, String.valueOf(numberOfQuestions) + " == ", Toast.LENGTH_SHORT).show();

                optionA.setEnabled(false);
                optionB.setEnabled(false);
                optionC.setEnabled(false);
                optionD.setEnabled(false);

                String correctAns = list.get(pos - 1).getCorrectAns();

                optionA.setBackgroundColor(Color.RED);

                if (correctAns.equalsIgnoreCase("a")) {
                    score++;
                    optionA.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("b")) {
                    optionB.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("c")) {
                    optionC.setBackgroundColor(Color.GREEN);
                } else {
                    optionD.setBackgroundColor(Color.GREEN);
                }
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionA.setEnabled(false);
                optionB.setEnabled(false);
                optionC.setEnabled(false);
                optionD.setEnabled(false);

                String correctAns = list.get(pos - 1).getCorrectAns();

                optionB.setBackgroundColor(Color.RED);

                if (correctAns.equalsIgnoreCase("a")) {
                    optionA.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("b")) {
                    score++;
                    optionB.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("c")) {
                    optionC.setBackgroundColor(Color.GREEN);
                } else {
                    optionD.setBackgroundColor(Color.GREEN);
                }
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionB.setEnabled(false);
                optionC.setEnabled(false);
                optionA.setEnabled(false);
                optionD.setEnabled(false);
                String correctAns = list.get(pos - 1).getCorrectAns();

                optionC.setBackgroundColor(Color.RED);

                if (correctAns.equalsIgnoreCase("a")) {
                    optionA.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("b")) {
                    optionB.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("C")) {
                    score++;
                    optionC.setBackgroundColor(Color.GREEN);
                } else {
                    optionD.setBackgroundColor(Color.GREEN);
                }
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionB.setEnabled(false);
                optionD.setEnabled(false);
                optionC.setEnabled(false);
                optionA.setEnabled(false);

                String correctAns = list.get(pos - 1).getCorrectAns();

                optionD.setBackgroundColor(Color.RED);

                if (correctAns.equalsIgnoreCase("a")) {
                    optionA.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("b")) {
                    optionB.setBackgroundColor(Color.GREEN);
                } else if (correctAns.equalsIgnoreCase("c")) {
                    optionC.setBackgroundColor(Color.GREEN);
                } else {
                    score++;
                    optionD.setBackgroundColor(Color.GREEN);
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionA.setEnabled(true);
                optionB.setEnabled(true);
                optionC.setEnabled(true);
                optionD.setEnabled(true);

                Toast.makeText(QuestionsActivity.this, String.valueOf(pos), Toast.LENGTH_SHORT).show();

                if (pos >= numberOfQuestions) {
                    next.setEnabled(false);
                    Intent intent = new Intent(QuestionsActivity.this, scoreActivity.class);
                    intent.putExtra("Score", score);
                    intent.putExtra("OutOf", numberOfQuestions);
                    startActivity(intent);
                    finish();
                } else if (pos < numberOfQuestions) {
                    question.setText(list.get(pos).getQuestion());
                    optionA.setText(list.get(pos).getOptionA());
                    optionB.setText(list.get(pos).getOptionB());
                    optionC.setText(list.get(pos).getOptionC());
                    optionD.setText(list.get(pos).getOptionD());

                    optionA.setBackgroundColor(0x00000000);
                    optionB.setBackgroundColor(0x00000000);
                    optionC.setBackgroundColor(0x00000000);
                    optionD.setBackgroundColor(0x00000000);

                } else {
                    Toast.makeText(QuestionsActivity.this, "How is this possible", Toast.LENGTH_SHORT).show();
                }
                pos++;


//                Toast.makeText(QuestionsActivity.this, String.valueOf(score), Toast.LENGTH_SHORT).show();
            }
        });
    }
}