package com.example.firus.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firus.DBhandler;
import com.example.firus.DBookmark;
import com.example.firus.Models.HistoryModel;
import com.example.firus.R;
import com.example.firus.Seaching;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewholder> {
    Context context;
    ArrayList<HistoryModel> list;
    DBhandler dBhandler;
    DBookmark dBookmark;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_history_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        HistoryModel historyModel = list.get(position);
        dBhandler = new DBhandler(context);
        dBookmark = new DBookmark(context);
        holder.history_title.setText(historyModel.getHistory_title());
        holder.history_url.setText(historyModel.getHistory_url());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Seaching.class);
                intent.putExtra("url",historyModel.getHistory_url());
                context.startActivity(intent);
            }
        });
        holder.history_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyItemRemoved(position);
                dBhandler.deletehistory(historyModel.getHistory_title());
                dBookmark.deletebookmark(historyModel.getHistory_title());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewholder extends RecyclerView.ViewHolder {
        ImageView history_delete;
        TextView history_title,history_url;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            history_title = itemView.findViewById(R.id.history_title);
            history_url = itemView.findViewById(R.id.history_url);
            history_delete = itemView.findViewById(R.id.history_delete);
        }
    }
}
