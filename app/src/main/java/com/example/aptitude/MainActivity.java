package com.example.aptitude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button;
    private static String imgurl = "https://gamespot1.cbsistatic.com/uploads/scale_landscape/1595/15950357/3653677-dragon%20ball%20z%20kakarot.jpeg";
    public static List<CategoryModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.collection("QUIZ").document("CATEGORIES").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    list.clear();
                    long count = documentSnapshot.getLong("COUNT");
                    for (int i = 1; i <= count; i++) {
                        list.add(new CategoryModel(imgurl, documentSnapshot.getString("CAT" + String.valueOf(i))));
                    }
                    button = findViewById(R.id.start_button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}