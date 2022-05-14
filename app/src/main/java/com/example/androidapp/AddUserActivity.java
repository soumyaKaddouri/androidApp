package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.model.Professor;
import com.example.androidapp.model.Student;
import com.example.androidapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity {

    EditText regEmail, regName, regPhoneNum, regPassword, regConformPassword;
    TextView loginHere;
    Button btnAdd;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    FirebaseAuth mAuth, mAuth2;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        regEmail = findViewById(R.id.inputEmail);
        regName = findViewById(R.id.inputUsername);
        regPhoneNum = findViewById(R.id.inputPhoneNum);
        regPassword = findViewById(R.id.inputPassword);
        regConformPassword = findViewById(R.id.inputConformPassword);
        radioGroup = findViewById(R.id.radio_group);

        btnAdd = findViewById(R.id.btnAdd);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnAdd.setOnClickListener(view ->{
            createUser();
        });

    }

    private void createUser(){
        String name = regName.getText().toString();
        String email = regEmail.getText().toString();
        String phoneNum = regPhoneNum.getText().toString();
        String password = regPassword.getText().toString();
        String conformPassword = regConformPassword.getText().toString();

        int selectedId= radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        final String selectedUser= radioButton.getText().toString();

        if (TextUtils.isEmpty(name)){
            regName.setError("Username cannot be empty");
            regName.requestFocus();
        }else if (TextUtils.isEmpty(email)){
            regEmail.setError("Email cannot be empty");
            regEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            regPassword.setError("Password cannot be empty");
            regPassword.requestFocus();
        }else if (TextUtils.isEmpty(conformPassword)){
            regConformPassword.setError("Password cannot be empty");
            regConformPassword.requestFocus();
        }else if (password.compareTo(conformPassword) != 0){
            regPassword.setError("Passwords are not matching");
            regPassword.requestFocus();
        }else{

            DocumentReference documentReference = db.collection("users").document(email);
            Map<String,Object> user = new HashMap<>();
            user.put("fName",name);
            user.put("email",email);
            user.put("phoneNum",phoneNum);
            user.put("type",selectedUser);
            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("TAG","onSuccess: user profile is created for "+ email);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG","onFailure:" +e.toString());
                }
            });

            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://iwimapp-7b2b0-default-rtdb.firebaseio.com/")
                    .setApiKey("AIzaSyBG2B3NlWO08UMd6cYTzVciCPf8E5vKzgU")
                    .setApplicationId("iwimapp-7b2b0").build();

            try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
                mAuth2 = FirebaseAuth.getInstance(myApp);
            } catch (IllegalStateException e){
                mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AppName"));
            }

            mAuth2.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //userId = mAuth.hetCurrentUser().grtUid();
                        Toast.makeText(AddUserActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddUserActivity.this, Home.class));

                    }else{
                        Toast.makeText(AddUserActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mAuth2.signOut();
                }
            });
        }
    }

}