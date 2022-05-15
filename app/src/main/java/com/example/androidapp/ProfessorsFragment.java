package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidapp.adapters.UserListAdapter;
import com.example.androidapp.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ProfessorsFragment extends Fragment implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("users");
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerView;
    UserListAdapter adapter;
    FloatingActionButton addUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_professors, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addUser = getActivity().findViewById(R.id.addUser);
        DocumentReference documentReference = db.collection("users").document(currentUser.getEmail());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    boolean isCord = task.getResult().getBoolean("isCord");
                    if(isCord){
                        addUser.setVisibility(View.VISIBLE);
                        addUser.setOnClickListener(view ->{
                            startActivity(new Intent(getActivity(), AddUserActivity.class));
                        });
                    }else{
                        addUser.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Intent intent = new Intent(getActivity(), Home.class);
                    startActivity(intent);
                }
            }
        });



        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        Query query = usersCollection.whereEqualTo("type","Professor").orderBy("fName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        System.out.println("Soumya"+ query + "  "+options);
        adapter= new UserListAdapter(options);
        System.out.println("Soumyaaaa "+adapter);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();

                //Toast.makeText(ListProfesseurs.this,"Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), UserData.class);
                intent.putExtra("SELECTED_USER_EMAIL", id);
                intent.putExtra("SELECTED_USER_ROLE", "Professor");
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

        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}