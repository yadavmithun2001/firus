package com.example.firus.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firus.Adapters.GridAdapter;
import com.example.firus.Adapters.NewsAdapter;
import com.example.firus.Models.Gridmodel;
import com.example.firus.Models.NewsModel;
import com.example.firus.R;
import com.example.firus.Seaching;
import com.google.android.material.chip.Chip;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home extends Fragment {
    EditText edit_search;
    ImageView imageView,voice_search;
    GridView gridView;
    ArrayList<Gridmodel> list;
    ArrayList<NewsModel> list1;
    LinearLayout lt_out;
    RecyclerView newsrecycler;
    ProgressBar pg;
    String url;
    Chip chip_topheadlines,chip_entertainment,chip_technology,chip_sports;
    NewsAdapter newsAdapter;
    CardView cardView;
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        imageView = view.findViewById(R.id.imageView);
        cardView = view.findViewById(R.id.cardview);
        edit_search = view.findViewById(R.id.edit_search);
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    performsearch();
                    return true;
                }
                return true;
            }
        });


        lt_out = view.findViewById(R.id.lt_out);

        voice_search = view.findViewById(R.id.voice_search);
        voice_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                startActivityForResult(intent,1);
            }
        });

        gridView = view.findViewById(R.id.gridview);
        list = new ArrayList<>();
        list.add(new Gridmodel(R.drawable.ic__534129544,"Google","www.google.com"));
        list.add(new Gridmodel(R.drawable.binglogo,"Bing","https://www.bing.com"));
        list.add(new Gridmodel(R.drawable.channels4_profile,"Meta","www.facebook.com"));
        list.add(new Gridmodel(R.drawable.youtube_icon,"YouTube","www.youtube.com"));
        list.add(new Gridmodel(R.drawable.flip_icon,"Flipkart","www.flipkart.com"));
        list.add(new Gridmodel(R.drawable.mynta_icon,"Myntra","www.myntra.com"));
        list.add(new Gridmodel(R.drawable.img_1,"Hindustan Times","www.hindustantimes.com"));
        GridAdapter gridAdapter = new GridAdapter(getActivity(),list);
        gridView.setAdapter(gridAdapter);

        list1 = new ArrayList<>();
        newsrecycler = view.findViewById(R.id.news_recycler);
        newsrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        pg = view.findViewById(R.id.progressBar);
        chip_topheadlines = view.findViewById(R.id.chip_headlines);
        chip_entertainment = view.findViewById(R.id.chip_entertainment);
        chip_technology = view.findViewById(R.id.chip_technology);
        chip_sports = view.findViewById(R.id.chip_sports);
        chip_topheadlines.setChipBackgroundColor(getResources().getColorStateList(R.color.txt,null));


        try {
            url = "https://newsapi.org/v2/top-headlines?country=in&ln=hi&category=general&apiKey=1ea0fe76a191482bb15fc9e5f45f46dd";
            loadnews(url);
        }catch (Exception e){
            FancyToast.makeText(getActivity(),e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
        }

        chip_topheadlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chip_entertainment.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_entertainment.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_entertainment.setTextColor(getResources().getColor(R.color.black));
                }
                if(chip_sports.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_sports.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_sports.setTextColor(getResources().getColor(R.color.black));

                }
                if(chip_technology.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_technology.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_technology.setTextColor(getResources().getColor(R.color.black));
                }
                chip_topheadlines.setChipBackgroundColor(getResources().getColorStateList(R.color.txt,null));
                pg.setVisibility(View.VISIBLE);
                list1.clear();
                chip_topheadlines.setTextColor(getResources().getColor(R.color.white));
                loadnews(url);
            }
        });

        chip_entertainment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(chip_topheadlines.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_topheadlines.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_topheadlines.setTextColor(getResources().getColor(R.color.black));
                }
                if(chip_sports.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_sports.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_sports.setTextColor(getResources().getColor(R.color.black));

                }
                if(chip_technology.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_technology.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_technology.setTextColor(getResources().getColor(R.color.black));
                }

                chip_entertainment.setChipBackgroundColor(getResources().getColorStateList(R.color.txt,null));
                pg.setVisibility(View.VISIBLE);
                list1.clear();
                chip_entertainment.setTextColor(getResources().getColor(R.color.white));
                String url1 = "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=1ea0fe76a191482bb15fc9e5f45f46dd";
                loadnews(url1);
            }
        });

        chip_sports.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(chip_topheadlines.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_topheadlines.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_topheadlines.setTextColor(getResources().getColor(R.color.black));
                }
                if(chip_entertainment.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_entertainment.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_entertainment.setTextColor(getResources().getColor(R.color.black));
                }
                if(chip_technology.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_technology.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_technology.setTextColor(getResources().getColor(R.color.black));
                }
                chip_sports.setChipBackgroundColor(getResources().getColorStateList(R.color.txt,null));
                pg.setVisibility(View.VISIBLE);
                list1.clear();
                chip_sports.setTextColor(getResources().getColor(R.color.white));
                String url1 = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=1ea0fe76a191482bb15fc9e5f45f46dd";
                loadnews(url1);
            }
        });
        chip_technology.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(chip_topheadlines.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_topheadlines.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_topheadlines.setTextColor(getResources().getColor(R.color.black));
                }
                if(chip_entertainment.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_entertainment.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_entertainment.setTextColor(getResources().getColor(R.color.black));
                }
                if(chip_sports.getChipBackgroundColor().equals(getResources().getColorStateList(R.color.txt,null))){
                    chip_sports.setChipBackgroundColor(getResources().getColorStateList(R.color.background,null));
                    chip_sports.setTextColor(getResources().getColor(R.color.black));
                }
                chip_technology.setChipBackgroundColor(getResources().getColorStateList(R.color.txt,null));
                pg.setVisibility(View.VISIBLE);
                list1.clear();
                chip_technology.setTextColor(getResources().getColor(R.color.white));
                String url1 = "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=1ea0fe76a191482bb15fc9e5f45f46dd";
                loadnews(url1);
            }
        });


        LinearLayout newslinearlayout = view.findViewById(R.id.newslinear);
        TextView txt_discovers = view.findViewById(R.id.txt_discovers);

        newsrecycler.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>oldScrollY){
                    gridView.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    cardView.animate().translationY(150);
                    newslinearlayout.animate().translationY(150);
                }
            }
        });
        newslinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                cardView.animate().translationY(-20);
                gridView.setVisibility(View.VISIBLE);
                newslinearlayout.animate().translationY(-10);

            }
        });
        txt_discovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                cardView.animate().translationY(150);
                newslinearlayout.animate().translationY(150);
            }
        });


        return view;
    }

    private void performsearch() {
        InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edit_search.getWindowToken(), 0);
        if(edit_search.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Please Search Something", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(getActivity(),Seaching.class);
            intent.putExtra("url",edit_search.getText().toString());
            getContext().startActivity(intent);
        }
    }


    private void loadnews(String url){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        String myresponse = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray jsonArray = new JSONObject(myresponse).getJSONArray("articles");
                                    for(int i = 0;i<jsonArray.length();i++){
                                        try {
                                            String title = jsonArray.getJSONObject(i).getString("title");
                                            String author = jsonArray.getJSONObject(i).getString("description");
                                            String subtitle = jsonArray.getJSONObject(i).getString("urlToImage");
                                            String url = jsonArray.getJSONObject(i).getString("url");
                                            NewsModel newsModel = new NewsModel(subtitle,url,title,author);
                                            list1.add(newsModel);
                                        }catch (Exception e){
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    newsAdapter = new NewsAdapter(getActivity(),list1);
                                    newsrecycler.setAdapter(newsAdapter);
                                    pg.setVisibility(View.GONE);

                                }catch (Exception e){
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:{
                if(resultCode == Activity.RESULT_OK && null != data){
                    Intent intent = new Intent(getActivity(),Seaching.class);
                    intent.putExtra("url",data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
                    startActivity(intent);
                }
            }
        }
    }


}