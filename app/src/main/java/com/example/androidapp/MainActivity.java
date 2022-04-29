package com.example.androidapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button btnPrf, btnStd, btnLogOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogOut = findViewById(R.id.button_logout);
        btnPrf = findViewById(R.id.button_profs);
        btnStd = findViewById(R.id.button_students);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        btnPrf.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, Profs_list.class));
        });
        btnStd.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, StudentsList.class));
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}