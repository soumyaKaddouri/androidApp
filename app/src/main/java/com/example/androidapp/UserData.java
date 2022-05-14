package com.example.androidapp;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class UserData extends AppCompatActivity {
    TextView fullName, email, phoneNum, fName;
    FloatingActionButton msg,callButton, sendEmail;
    String userSelectedEmail, userSelectedRole;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

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

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0666189113"));
                startActivity(intent);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "red@gmail.com";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                startActivity(Intent.createChooser(emailIntent, "Send an email with : "));
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "0623151223";
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
                startActivity(smsIntent);
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
                    fullName.setText(documentSnapshot.get("fName").toString());
                    email.setText(documentSnapshot.get("email").toString());
                    phoneNum.setText(documentSnapshot.get("phoneNum").toString());
                    fName.setText(documentSnapshot.get("fName").toString());
//                    if (userSelectedRole.equals("Student")){
//                        courses.setVisibility(View.GONE);
//                        courses_names.setVisibility(View.GONE);
//                    }
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