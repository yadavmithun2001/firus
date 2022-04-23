package com.example.firus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firus.MainActivity;
import com.example.firus.Models.NewsModel;
import com.example.firus.R;
import com.example.firus.Seaching;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewholder> {
    Context context;
    ArrayList<NewsModel> list;

    public NewsAdapter(Context context, ArrayList<NewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_news_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        NewsModel newsModel = list.get(position);
        holder.title.setText(newsModel.getNews_title());
        holder.subtitle.setText(newsModel.getNews_subtitle());

        Picasso.with(context).load(newsModel.getThumbnail()).into(holder.thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Seaching.class);
                intent.putExtra("url",newsModel.getUrl());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView thumbnail;
        TextView title,subtitle;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.news_title);
            subtitle = itemView.findViewById(R.id.news_sub_title);
        }
    }
}
