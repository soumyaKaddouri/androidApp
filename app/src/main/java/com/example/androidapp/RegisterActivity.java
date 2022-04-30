package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    EditText regEmail, regName, regPhoneNum, regPassword, regConformPassword;
    TextView loginHere;
    Button btnRegister;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail = findViewById(R.id.inputEmail);
        regName = findViewById(R.id.inputUsername);
        regPhoneNum = findViewById(R.id.inputPhoneNum);
        regPassword = findViewById(R.id.inputPassword);
        regConformPassword = findViewById(R.id.inputConformPassword);
        radioGroup = findViewById(R.id.radio_group);

        loginHere = findViewById(R.id.alreadyHaveAccount);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        loginHere.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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

        if (TextUtils.isEmpty(email)){
            regEmail.setError("Email cannot be empty");
            regEmail.requestFocus();
        }else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(conformPassword)){
            regPassword.setError("Password cannot be empty");
            regPassword.requestFocus();
        }else if (password.compareTo(conformPassword) == 0){
            regPassword.setError("Password are not matching");
            regPassword.requestFocus();
        }else if (TextUtils.isEmpty(name)){
            regPassword.setError("Username cannot be empty");
            regPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                       User user = new User(name,email,phoneNum,selectedUser);
                       db.collection("Student").document(email).set(user);
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, Home.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}