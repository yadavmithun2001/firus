package com.example.firus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.service.quickaccesswallet.WalletCard;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.firus.fragments.Home;
import com.example.firus.fragments.Profile;
import com.example.firus.fragments.Shorts;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.ParseInstallation;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView nav;
    BottomSheetDialog dialog;
    TextView history,bookmarks,downloads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav = findViewById(R.id.navview);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();

        dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_more);
        history = dialog.findViewById(R.id.history);
        bookmarks = dialog.findViewById(R.id.bookmark);
        downloads = dialog.findViewById(R.id.downloads);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        int network = Networkstatus.getnetwork(this);
        if(network !=1 && network !=2){
            Intent intent = new Intent(MainActivity.this,Seaching.class);
            intent.putExtra("url","www.google.com");
            startActivity(intent);
            finish();
        }else {
            fm.beginTransaction().replace(R.id.framelayout,new Home()).commit();
        }


        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        fm.beginTransaction().replace(R.id.framelayout,new Home()).commit();
                        return true;
                    case R.id.shorts:
                        Intent intent = new Intent(MainActivity.this, wallet.class);
                        startActivity(intent);
                        return true;
                    case R.id.more:
                        dialog.show();
                        return true;
                    case R.id.profile:
                        fm.beginTransaction().replace(R.id.framelayout,new Profile()).commit();
                        return true;
                }
                return true;
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,History.class);
                intent.putExtra("historytxt","History");
                startActivity(intent);
            }
        });
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,History.class);
                intent.putExtra("historytxt","Bookmarks");
                startActivity(intent);
            }
        });
        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Downloads.class);
                startActivity(intent);
            }
        });



    }



}