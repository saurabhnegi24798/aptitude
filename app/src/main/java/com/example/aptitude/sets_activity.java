package com.example.aptitude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class sets_activity extends AppCompatActivity {

    private GridView gridView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SETS");

        final String category = getIntent().getStringExtra("POSITION");
        db.collection("QUIZ").document(category).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    long sets = documentSnapshot.getLong("SETS");
                    gridView = findViewById(R.id.gridview);
                    GridAdapter gridAdapter = new GridAdapter((int) sets, category);
                    gridView.setAdapter(gridAdapter);
                } else
                    Toast.makeText(sets_activity.this, "Please Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(sets_activity.this, "Please Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
