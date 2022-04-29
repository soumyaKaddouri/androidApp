package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.example.androidapp.adapters.ProfAdapter;
import com.example.androidapp.model.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class Profs_list extends AppCompatActivity {
    ListView list_prof;
    LinkedList<Professeur> profs;
    FirebaseFirestore db;

    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profs_list);

        list_prof=(ListView) findViewById(R.id.list_view);
        db=FirebaseFirestore.getInstance();


        profs = new LinkedList<Professeur>();
        getAllProfesseurs();
    }
    void getAllProfesseurs(){

        showProgressDialog();
        db.collection("professeur")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getString("nom"));
                                System.out.println(document.getString("prenom"));
                                System.out.println(document.getString("tel"));
                                System.out.println(document.getString("photo"));
                                System.out.println(document.getString("departement"));
                                Professeur p = new Professeur(
                                        document.getString("nom"),
                                        document.getString("prenom"),
                                        document.getString("tel"),
                                        document.getString("photo"),
                                        document.getString("departement"));
                                profs.add(p);
                            }
                            System.out.println(profs);
                        } else {
                            System.out.println("Erreur");
                        }
                        hideProgressDialog();

                        ListView profView= findViewById(R.id.list_view);
                        profView.setAdapter(new ProfAdapter(Profs_list.this,profs));


                    }
                });
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


}

