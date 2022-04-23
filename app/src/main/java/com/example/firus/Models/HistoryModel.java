package com.example.firus.Models;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryModel {
    private String history_title;
    private String history_url;
    private int id;

    public HistoryModel(String history_title, String history_url){
        this.history_title=history_title;
        this.history_url=history_url;
    }

    public HistoryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistory_title() {
        return history_title;
    }

    public void setHistory_title(String history_title) {
        this.history_title = history_title;
    }

    public String getHistory_url() {
        return history_url;
    }

    public void setHistory_url(String history_url) {
        this.history_url = history_url;
    }
}
