package com.example.firus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firus.Models.Gridmodel;
import com.example.firus.R;
import com.example.firus.Seaching;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<Gridmodel> {
    public GridAdapter(@NonNull Context context, ArrayList<Gridmodel> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View gridview = convertView;
        if(gridview == null){
            gridview = LayoutInflater.from(getContext()).inflate(R.layout.sample_gridview,parent,false);
        }
        Gridmodel gridmodel = getItem(position);
        TextView item_title = gridview.findViewById(R.id.item_url);
        ImageView item_icon = gridview.findViewById(R.id.item_icon);
        item_title.setText(gridmodel.getItem_title());
        item_icon.setImageResource(gridmodel.getItem_icon());
        item_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Seaching.class);
                intent.putExtra("url",gridmodel.getItem_url());
                getContext().startActivity(intent);
            }
        });

        return gridview;
    }
}
