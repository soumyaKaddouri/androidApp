package com.example.androidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidapp.R;
import com.example.androidapp.model.Professor;

import java.util.LinkedList;


public class ProfAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<Professor> professors;
    private LayoutInflater inflater;

    public ProfAdapter(Context context, LinkedList<Professor> professors) {
        this.context = context;
        this.professors = professors;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return professors.size();
    }

    @Override
    public Professor getItem(int position) {
        return professors.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.adapter_item,null);

        Professor currentProf = getItem(i);
        String itemName= currentProf.getNom();
        String itemPrenom= currentProf.getPrenom();
        String itemDepatement=currentProf.getDepartement();
        String itemPhoto=currentProf.getPhoto();
        String itemTel=currentProf.getTel();

        TextView itemNameView = view.findViewById(R.id.name_prof);
        itemNameView.setText(itemName);

        TextView itemDepatementView = view.findViewById(R.id.departement_prof);
        itemDepatementView.setText(itemDepatement);


        return view;
    }
}