package com.example.androidapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapp.model.UploadPDF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadFileActivity extends AppCompatActivity {

    EditText editPdfName;
    Button uploadBtn;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        editPdfName = findViewById(R.id.textPdfName);
        uploadBtn = findViewById(R.id.uploadBtn);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDFFile();
            }
        });

    }

    private void selectPDFFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode == RESULT_OK && data !=null && data.getData()!=null){
            uploadPdfFile(data.getData());
        }
    }

    private void uploadPdfFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference=storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()){
                    Uri uri = uriTask.getResult();
                    UploadPDF uploadPDF = new UploadPDF(editPdfName.getText().toString(),uri.toString());
                    databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                    Toast.makeText(UploadFileActivity.this, "File Uploaded",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: "+(int) progress+"%");
            }
        });
    }
}