package com.example.androidapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class UserData extends AppCompatActivity {
    TextView fullName, email, phoneNum, fName;
    FloatingActionButton msg,callButton, sendEmail, backBtn;
    String userSelectedEmail, userSelectedRole;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DocumentReference documentReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSelectedEmail = getIntent().getStringExtra("SELECTED_USER_EMAIL");
        userSelectedRole = getIntent().getStringExtra("SELECTED_USER_ROLE");

        setContentView(R.layout.activity_user_data);
        fullName = findViewById(R.id.textVShowFullName);
        email = findViewById(R.id.textVShowEmail);
        phoneNum = findViewById(R.id.textVShowPhone);
        fName = findViewById(R.id.textFName);

        msg = findViewById(R.id.msg);
        callButton = findViewById(R.id.call);
        sendEmail = findViewById(R.id.senEmail);
        User myUser;

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserData.this, Home.class));
            }
        });

        loadUserData();
    }

    private void loadUserData() {
        db.collection("users").document(userSelectedEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot==null){
                    Toast.makeText(UserData.this, "Error Loading Info", Toast.LENGTH_SHORT).show();
                }else{
                    User myUser = new User();
                    myUser.setfName(documentSnapshot.get("fName").toString());
                    myUser.setEmail(documentSnapshot.get("email").toString());
                    myUser.setPhoneNum(documentSnapshot.get("phoneNum").toString());
                    myUser.setType(documentSnapshot.get("fName").toString());
                    fullName.setText(myUser.getfName());
                    email.setText(myUser.getEmail());
                    phoneNum.setText(myUser.getPhoneNum());
                    fName.setText(myUser.getfName());


                    callButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);

                            intent.setData(Uri.parse("tel:"+myUser.getPhoneNum()));
                            startActivity(intent);
                        }
                    });

                    sendEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + myUser.getEmail()));
                            startActivity(Intent.createChooser(emailIntent, "Send an email with : "));
                        }
                    });

                    msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + myUser.getPhoneNum()));
                            startActivity(smsIntent);
                        }
                    });

                }
            }
        });


    }
//    public void sendMessage (View v){
//        Intent intent = new Intent(this,Messagerie.class);
//        intent.putExtra("SELECTED_USER_EMAIL", userSelectedEmail);
//        startActivity(intent);
//    }


}