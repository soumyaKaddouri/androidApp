package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity {
    TextView fName, type;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);



        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference documentReference;
        documentReference = db.collection("users").document(user.getEmail());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    fName = navigationView.findViewById(R.id.fullName);
                    type = navigationView.findViewById(R.id.type);
                    String nameRes = task.getResult().getString("fName");
                    String typeRes = task.getResult().getString("type");
                    System.out.println(nameRes);
                    System.out.println(typeRes);
                    System.out.println(fName);
                    fName.setText(nameRes);
                    type.setText(typeRes);


                }else{
                    Intent intent = new Intent(Home.this, ProfileFragment.class);
                    startActivity(intent);
                }
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView textTile = findViewById(R.id.textTitle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                textTile.setText(navDestination.getLabel());
            }
        });

//        btnLogOut = findViewById(R.id.button_logout);
//        btnPrf = findViewById(R.id.button_profs);
//        btnStd = findViewById(R.id.button_students);
        mAuth = FirebaseAuth.getInstance();
//
//        btnLogOut.setOnClickListener(view ->{
//            mAuth.signOut();
//            startActivity(new Intent(Home.this, LoginActivity.class));
//        });
//
//        btnPrf.setOnClickListener(view ->{
//            startActivity(new Intent(Home.this, Profs_list.class));
//        });
//        btnStd.setOnClickListener(view ->{
//            startActivity(new Intent(Home.this, StudentsList.class));
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(Home.this, LoginActivity.class));
        }
    }
}