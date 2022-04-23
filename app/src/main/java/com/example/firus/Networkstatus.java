package com.example.firus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networkstatus {
    public static int getnetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            switch (networkInfo.getType()){
                case ConnectivityManager.TYPE_WIFI:
                    return 1;
                case ConnectivityManager.TYPE_MOBILE:
                    return 2;
                default:
                    return 0;
            }
        }
        else {
            return 100;
        }
    }
}
