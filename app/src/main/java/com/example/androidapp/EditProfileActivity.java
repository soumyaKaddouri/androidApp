package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapp.adapters.UserListAdapter;
import com.example.androidapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

public class EditProfileActivity extends AppCompatActivity {
    EditText fName, phoneNumber, email;
    Button updateProfile;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    User myUser;
    DocumentReference documentReference;
    FloatingActionButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fName = findViewById(R.id.updateName);
        email = findViewById(R.id.updateEmail);
        phoneNumber = findViewById(R.id.updatePhone);
        updateProfile = findViewById(R.id.btnUpdateProfile);
        backBtn = findViewById(R.id.backButton);

        documentReference = db.collection("users").document(user.getEmail());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()) {
                    myUser = new User();
                    myUser.setfName(task.getResult().getString("fName"));
                    myUser.setEmail(task.getResult().getString("email"));
                    myUser.setPhoneNum(task.getResult().getString("phoneNum"));
                    myUser.setType(task.getResult().getString("type"));

                    fName.setText(myUser.getfName());
                    System.out.println("HHHHHHHHHHH"+myUser.getfName());
                    email.setText(myUser.getEmail());
                    phoneNumber.setText(myUser.getPhoneNum());
                }
            }
        });
        updateProfile.setOnClickListener(view -> {
            updateProfile();
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, Home.class));
            }
        });

    }
    private void updateProfile(){
        User newUser = new User();
        newUser.setfName(fName.getText().toString());
        newUser.setEmail(email.getText().toString());
        newUser.setPhoneNum(phoneNumber.getText().toString());
        documentReference.update("fName", newUser.getfName(),"email",newUser.getEmail(),"phoneNum",newUser.getPhoneNum()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this,"Profile updated successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}