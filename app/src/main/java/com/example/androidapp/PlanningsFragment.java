package com.example.androidapp;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.adapters.PdfListAdapter;
import com.example.androidapp.model.UploadPDF;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PlanningsFragment extends Fragment {
    FirebaseFirestore db;
    ListView myPdfFilesView;
    DatabaseReference databaseReference;
    List<UploadPDF> uploadPDFs;
    PdfListAdapter myAdapter;
    FloatingActionButton addPlanning;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_plannings, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myPdfFilesView = getActivity().findViewById(R.id.myListView);
        uploadPDFs = new ArrayList<>();

        addPlanning = getActivity().findViewById(R.id.addPlanning);
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(currentUser.getEmail());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    boolean isCord = task.getResult().getBoolean("isCord");
                    if(isCord){
                        addPlanning.setVisibility(View.VISIBLE);
                        addPlanning.setOnClickListener(view ->{
                            startActivity(new Intent(getActivity(), UploadFileActivity.class));
                        });
                    }else{
                        addPlanning.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Intent intent = new Intent(getActivity(), Home.class);
                    startActivity(intent);
                }
            }
        });

        viewAllFiles();

        myPdfFilesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UploadPDF uploadPDF = uploadPDFs.get(i);

                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadPDF.getUrl()));
                startActivity(intent);
            }
        });


    }

    private void viewAllFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        System.out.println("HHHHHHHHHHHH"+databaseReference);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    UploadPDF uploadPdf = postSnapshot.getValue(UploadPDF.class);
                    System.out.println("Souma"+uploadPdf);
                    uploadPDFs.add(uploadPdf);
                }
                String[] uploads = new String[uploadPDFs.size()];
                for(int i=0;i<uploads.length;i++){
                    uploads[i] = uploadPDFs.get(i).getName();
                    System.out.println("HHHHHHHHHH"+uploads[i]);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,uploads){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position,convertView,parent);
                        TextView myText = view.findViewById(android.R.id.text1);
                        myText.setTextColor(Color.BLACK);

                        return view;
                    }
                };
                myPdfFilesView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}