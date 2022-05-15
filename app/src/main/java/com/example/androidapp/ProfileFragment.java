package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView fName, fNameUser, phoneNumber, email, type;
    Button editProfile;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference documentReference;
        documentReference = db.collection("users").document(user.getEmail());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String nameRes = task.getResult().getString("fName");
                    String emailRes = task.getResult().getString("email");
                    String phoneRes = task.getResult().getString("phoneNum");
                    String typeRes = task.getResult().getString("type");

                    fName.setText(nameRes);
                    fNameUser.setText(nameRes);
                    email.setText(emailRes);
                    phoneNumber.setText(phoneRes);
                    type.setText(typeRes);

                }else{
                    Intent intent = new Intent(getActivity(), Home.class);
                    startActivity(intent);
                }
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fNameUser = getActivity().findViewById(R.id.fNameUser);
        fName = getActivity().findViewById(R.id.fName);
        type = getActivity().findViewById(R.id.userType);
        phoneNumber = getActivity().findViewById(R.id.textViewPhone);
        email = getActivity().findViewById(R.id.textViewEmail);
        editProfile = getActivity().findViewById(R.id.btnEditProfile);



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}