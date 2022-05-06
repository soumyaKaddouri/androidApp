package com.example.androidapp.adapters;

import com.example.androidapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidapp.model.User;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UserListAdapter  extends FirestoreRecyclerAdapter<User,UserListAdapter.UserListViewHolder> {
    //private ArrayList<User> postList;
    private OnItemClickListener listener;


    public class UserListViewHolder extends RecyclerView.ViewHolder {
        public TextView fullName, email, phone;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName= itemView.findViewById(R.id.nameText);
            email= itemView.findViewById(R.id.emailText);
            phone = itemView.findViewById(R.id.phoneText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserListAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @NonNull
    @Override
    public UserListAdapter.UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new UserListViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull UserListViewHolder holder, int position, @NonNull User model) {
        holder.fullName.setText(model.getfName());
        System.out.println("Soumaaaaaa" + model.toString());
        holder.phone.setText(model.getPhoneNum());
        holder.email.setText(model.getEmail());
    }

}