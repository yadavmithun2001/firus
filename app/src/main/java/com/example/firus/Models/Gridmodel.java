package com.example.firus.Models;

public class Gridmodel {
    private int item_icon;
    private String item_title;
    private String Item_url;

    public Gridmodel(int item_icon, String item_title,String item_url) {
        this.item_icon = item_icon;
        this.item_title = item_title;
        this.Item_url = item_url;
    }
    public Gridmodel(){
    }

    public String getItem_url() {
        return Item_url;
    }

    public void setItem_url(String item_url) {
        Item_url = item_url;
    }

    public int getItem_icon() {
        return item_icon;
    }

    public void setItem_icon(int item_icon) {
        this.item_icon = item_icon;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }
}
