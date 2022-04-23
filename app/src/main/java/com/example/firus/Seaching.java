package com.example.firus;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firus.Adapters.HistoryAdapter;
import com.example.firus.Models.HistoryModel;

import java.util.ArrayList;

public class Seaching extends AppCompatActivity {
    EditText edit_search;
    WebView webView;
    ProgressBar progressBar;
    TextView searched_txt;
    CardView cardView;
    ImageView more,voice_search,home,add_bookmark;
    String geturl;
    DBhandler dBhandler;
    DBookmark dBookmark;
    LinearLayout lt_more;
    TextView reload,history,bookmarks,sharelink,downloads,settings,noconnection;
    ArrayList<HistoryModel> list;

    @SuppressLint({"SetJavaScriptEnabled", "ResourceAsColor"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seaching);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        webView = findViewById(R.id.webview);
        int networkget = Networkstatus.getnetwork(Seaching.this);
        noconnection = findViewById(R.id.noconnection);
        if(networkget != 1 && networkget != 2){
           webView.setVisibility(View.GONE);
           noconnection.setVisibility(View.VISIBLE);
        }


        dBhandler = new DBhandler(this);
        dBookmark = new DBookmark(Seaching.this);
        list = new ArrayList<>();
        bookmarks = findViewById(R.id.bookmarks);
        geturl = getIntent().getExtras().getString("url");
        searched_txt = findViewById(R.id.searched_txt);
        searched_txt.setVisibility(View.GONE);
        cardView = findViewById(R.id.cardview);
        voice_search = findViewById(R.id.voice_search);
        voice_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent,1);
            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seaching.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        more = findViewById(R.id.more);
        lt_more = findViewById(R.id.lt_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(lt_more.isActivated()){
                   lt_more.setVisibility(View.GONE);
                   lt_more.setActivated(false);
               }else {
                   lt_more.setVisibility(View.VISIBLE);
                   lt_more.setActivated(true);
               }
            }
        });
        edit_search = findViewById(R.id.edit_search);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.supportZoom();
        webSettings.setLoadWithOverviewMode(true);

        add_bookmark = findViewById(R.id.add_bookmark);
        add_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dBookmark.savebookmark(webView.getTitle(), webView.getUrl());
                add_bookmark.setColorFilter(getResources().getColor(R.color.purple_700));
                Toast.makeText(Seaching.this, webView.getTitle()+" Saved to Bookmarks", Toast.LENGTH_SHORT).show();
            }
        });

        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                lt_more.setVisibility(View.GONE);
                lt_more.setActivated(false);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    progressBar.setVisibility(View.INVISIBLE);
                    edit_search.setVisibility(View.GONE);
                    searched_txt.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);;
                }
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                searched_txt.setText(title);
                super.onReceivedTitle(view, title);
                dBhandler.savehistory(title,view.getUrl());
                list = dBookmark.loadbookmark();
                for(int i =0;i<list.size();i++){
                    if(list.get(i).getHistory_url().equals(webView.getUrl())){
                        add_bookmark.setColorFilter(getResources().getColor(R.color.purple_200));
                    }
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        if(!geturl.isEmpty()){
            if(geturl.startsWith("www")){
                webView.loadUrl("https://"+geturl);
            }else if(geturl.startsWith("https://")){
                webView.loadUrl(geturl);
            }else {
                webView.loadUrl("https://www.google.com/search?q="+geturl+"&sxsrf=AOaemvIJQw1rydjJRFgK1wddqh8IUdGKJg%3A1636702343136&source=hp&ei=hxiOYb7BBpnC5OUP4NajiAE&iflsig=ALs-wAMAAAAAYY4ml4_ih2TirQqXG-0PvhIerUu5FrCQ&oq=" +geturl+ "&gs_lcp=Cgdnd3Mtd2l6EAMyBAgjECcyBAgjECcyBAgjECcyBwguELEDEEMyBwgAELEDEEMyBAgAEEMyBAgAEEMyBAguEEMyBAguEEMyBAguEEM6CgguEMcBEKMCEEM6BQgAEJECOgsIABCABBCxAxCDAToICAAQgAQQsQM6CAguEIAEELEDUABY_AVgowhoAHAAeACAAbYBiAHvBpIBAzAuNZgBAKABAQ&sclient=gws-wiz&ved=0ahUKEwj-oI6up5L0AhUZIbkGHWDrCBEQ4dUDCAc&uact=5");
            }
        }

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
        searched_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searched_txt.setVisibility(View.GONE);
                edit_search.setText(webView.getUrl());
                edit_search.setSelectAllOnFocus(true);
                edit_search.requestFocus();
                edit_search.setVisibility(View.VISIBLE);
            }
        });
        reload = findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });


        history = findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seaching.this,History.class);
                intent.putExtra("historytxt","History");
                startActivity(intent);
            }
        });
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seaching.this,History.class);
                intent.putExtra("historytxt","Bookmarks");
                startActivity(intent);
            }
        });

        sharelink = findViewById(R.id.sharelink);
        sharelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject Here");
                intent.putExtra(Intent.EXTRA_TEXT,webView.getUrl());
                startActivity(Intent.createChooser(intent,"Share via"));
            }
        });


    }
    private void performsearch() {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edit_search.getWindowToken(), 0);
        if(edit_search.getText().toString().startsWith("www")){
            webView.loadUrl("https://"+edit_search.getText().toString());
        }else if(edit_search.getText().toString().startsWith("http")){
            webView.loadUrl(edit_search.getText().toString());
        }else {
            webView.loadUrl("https://www.google.com/search?q=" + edit_search.getText().toString() + "&sxsrf=AOaemvIJQw1rydjJRFgK1wddqh8IUdGKJg%3A1636702343136&source=hp&ei=hxiOYb7BBpnC5OUP4NajiAE&iflsig=ALs-wAMAAAAAYY4ml4_ih2TirQqXG-0PvhIerUu5FrCQ&oq=" + edit_search.getText().toString() + "&gs_lcp=Cgdnd3Mtd2l6EAMyBAgjECcyBAgjECcyBAgjECcyBwguELEDEEMyBwgAELEDEEMyBAgAEEMyBAgAEEMyBAguEEMyBAguEEMyBAguEEM6CgguEMcBEKMCEEM6BQgAEJECOgsIABCABBCxAxCDAToICAAQgAQQsQM6CAguEIAEELEDUABY_AVgowhoAHAAeACAAbYBiAHvBpIBAzAuNZgBAKABAQ&sclient=gws-wiz&ved=0ahUKEwj-oI6up5L0AhUZIbkGHWDrCBEQ4dUDCAc&uact=5");
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == Activity.RESULT_OK && null != data ){
                    webView.loadUrl("https://www.google.com/search?q=" + data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0) + "&sxsrf=AOaemvIJQw1rydjJRFgK1wddqh8IUdGKJg%3A1636702343136&source=hp&ei=hxiOYb7BBpnC5OUP4NajiAE&iflsig=ALs-wAMAAAAAYY4ml4_ih2TirQqXG-0PvhIerUu5FrCQ&oq=" + data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0) + "&gs_lcp=Cgdnd3Mtd2l6EAMyBAgjECcyBAgjECcyBAgjECcyBwguELEDEEMyBwgAELEDEEMyBAgAEEMyBAgAEEMyBAguEEMyBAguEEMyBAguEEM6CgguEMcBEKMCEEM6BQgAEJECOgsIABCABBCxAxCDAToICAAQgAQQsQM6CAguEIAEELEDUABY_AVgowhoAHAAeACAAbYBiAHvBpIBAzAuNZgBAKABAQ&sclient=gws-wiz&ved=0ahUKEwj-oI6up5L0AhUZIbkGHWDrCBEQ4dUDCAc&uact=5");
                }
        }
    }
}