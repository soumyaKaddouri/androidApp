package com.example.androidapp.adapters;

import com.example.androidapp.MyViewHolder;
import com.example.androidapp.R;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.model.UploadPDF;

import java.util.ArrayList;

public class PdfListAdapter extends RecyclerView.Adapter<MyViewHolder> {

    FragmentActivity mainActivity;
    ArrayList<UploadPDF> downModels;

    public PdfListAdapter(FragmentActivity mainActivity, ArrayList<UploadPDF> downModels) {
        this.mainActivity = mainActivity;
        this.downModels = downModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.elements, null, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mName.setText(downModels.get(position).getName());
        holder.mLink.setText(downModels.get(position).getUrl());
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(holder.mName.getContext(),downModels.get(position).getName(),".pdf",DIRECTORY_DOWNLOADS,downModels.get(position).getUrl());
            }
        });
    }



    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }


    @Override
    public int getItemCount() {
        return downModels.size();
    }
}
