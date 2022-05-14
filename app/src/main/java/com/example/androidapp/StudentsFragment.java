package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class StudentsFragment extends Fragment implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("users");
    RecyclerView recyclerView;
    UserListAdapter adapter;
    FloatingActionButton addUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_students, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addUser = getActivity().findViewById(R.id.addUser);
        addUser.setOnClickListener(view ->{
            startActivity(new Intent(getActivity(), AddUserActivity.class));
        });

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        Query query = usersCollection.whereEqualTo("type","Student").orderBy("fName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        //System.out.println("Soumya"+ query + "  "+options);
        adapter= new UserListAdapter(options);
        //System.out.println("Soumyaaaa "+adapter);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();

                Intent intent = new Intent(getActivity(), UserData.class);
                intent.putExtra("SELECTED_USER_EMAIL", id);
                intent.putExtra("SELECTED_USER_ROLE", "Student");
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