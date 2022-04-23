package com.example.firus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firus.Adapters.HistoryAdapter;
import com.example.firus.Models.HistoryModel;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView back;
    TextView history_bookmarks,clearall,emptytxt;
    ArrayList<HistoryModel> list_history,list_bookmark;
    DBhandler dBhandler;
    DBookmark dBookmark;
    HistoryAdapter adapter1,adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerview);
        history_bookmarks = findViewById(R.id.history_bookmark);
        history_bookmarks.setText(getIntent().getStringExtra("historytxt"));
        emptytxt = findViewById(R.id.emptytxt);
        dBhandler = new DBhandler(this);
        dBookmark = new DBookmark(this);
        list_history = new ArrayList<>();
        list_bookmark = new ArrayList<>();

        if(history_bookmarks.getText().equals("History")){
            list_history = dBhandler.loadhistory();
            if(list_history.isEmpty()){
                recyclerView.setVisibility(View.INVISIBLE);
                emptytxt.setVisibility(View.VISIBLE);
            }
            adapter1 = new HistoryAdapter(this,list_history);
            recyclerView.setAdapter(adapter1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
            recyclerView.scrollToPosition(list_history.size()-1);
        }else {
            list_bookmark = dBookmark.loadbookmark();
            if(list_bookmark.isEmpty()){
                recyclerView.setVisibility(View.INVISIBLE);
                emptytxt.setVisibility(View.VISIBLE);
            }
            adapter2 = new HistoryAdapter(this,list_bookmark);
            recyclerView.setAdapter(adapter2);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        clearall  = findViewById(R.id.clearall);
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(history_bookmarks.getText().equals("History")){
                    dBhandler.deleteall();
                    Toast.makeText(History.this, "All History Cleared", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }else {
                    dBookmark.deleteall();
                    Toast.makeText(History.this,"All Bookmarks Cleared",Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });

    }
}