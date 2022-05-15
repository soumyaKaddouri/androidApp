package com.example.androidapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.adapters.UserListAdapter;
import com.example.androidapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class UserData extends AppCompatActivity {
    TextView fullName, email, phoneNum, fName;
    FloatingActionButton msg,callButton, sendEmail, backBtn, deleteBtn, editBtn;
    String userSelectedEmail, userSelectedRole;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
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

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserData.this, UserListAdapter.class));
            }
        });

        deleteBtn = findViewById(R.id.deleteBtn);
        editBtn = findViewById(R.id.editBtn);

        DocumentReference documentReference = db.collection("users").document(currentUser.getEmail());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    boolean isCord = task.getResult().getBoolean("isCord");
                    if(isCord){
                        deleteBtn.setVisibility(View.VISIBLE);
                        editBtn.setVisibility(View.VISIBLE);
                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog diaBox = AskOption();
                                diaBox.show();
                            }
                        });
                        editBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(UserData.this, UpdateUserActivity.class);
                                intent.putExtra("SELECTED_USER_EMAIL", userSelectedEmail);
                                startActivity(intent);
                            }
                        });

                    }else{
                        deleteBtn.setVisibility(View.INVISIBLE);
                        editBtn.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Intent intent = new Intent(UserData.this, Home.class);
                    startActivity(intent);
                }
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
    private AlertDialog AskOption()
    {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_delete);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.BLUE);
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(wrappedDrawable)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        db.collection("users").document(userSelectedEmail).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(UserData.this,"Deleted",Toast.LENGTH_SHORT).show();
                                loadUserData();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserData.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

//    public void sendMessage (View v){
//        Intent intent = new Intent(this,Messagerie.class);
//        intent.putExtra("SELECTED_USER_EMAIL", userSelectedEmail);
//        startActivity(intent);
//    }


}