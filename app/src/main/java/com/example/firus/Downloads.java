package com.example.firus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firus.Adapters.DownloadAdapter;

import java.io.File;
import java.util.ArrayList;

public class Downloads extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView nodownloads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        
        recyclerView = findViewById(R.id.downloadrecycler);
        ArrayList<String> list = new ArrayList<>();
        nodownloads = findViewById(R.id.nodownlaods);

        if(checkpermisson()){
            File Download = new File(Environment.getExternalStorageDirectory(),"Download");
            String path = Download.getAbsolutePath();
            File root = new File(path);
            File[] filesandfolders = root.listFiles();

            DownloadAdapter adapter = new DownloadAdapter(this,filesandfolders);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            if(filesandfolders == null){
             recyclerView.setVisibility(View.INVISIBLE);
             nodownloads.setVisibility(View.VISIBLE);
            }
        }else
        {
            requestpermisson();
        }


    }
    private boolean checkpermisson(){
        int result = ContextCompat.checkSelfPermission(Downloads.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
    }
    private void requestpermisson() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Downloads.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Downloads.this, "Storage Permission is Required", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(Downloads.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }
    }
}