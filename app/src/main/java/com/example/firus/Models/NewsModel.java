package com.example.firus.Models;

import androidx.recyclerview.widget.RecyclerView;

public class NewsModel {
    String thumbnail,url;
    String news_title,news_subtitle;

    public NewsModel(String thumbnail,String url, String news_title, String news_subtitle) {
        this.thumbnail = thumbnail;
        this.url = url;
        this.news_title = news_title;
        this.news_subtitle = news_subtitle;
    }
    public NewsModel(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_subtitle() {
        return news_subtitle;
    }

    public void setNews_subtitle(String news_subtitle) {
        this.news_subtitle = news_subtitle;
    }
}
