package com.example.firus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class wallet extends AppCompatActivity implements PaymentStatusListener {
    TextView addmoney;
    final int PAY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String transcId = df.format(c);

        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
                .setPayeeVpa("7462074102@ybl")
                .setPayeeName("Mithun")
                .setTransactionId(transcId)
                .setTransactionRefId("0120192019060302240")
                .setPayeeMerchantCode("4582")
                .setDescription("For Today's Food")
                .setAmount("1.00")
                .build();

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "7462074102@ybl")
                .appendQueryParameter("pn", "mithun")
                .appendQueryParameter("tn", "notes")
                .appendQueryParameter("am", "1.00")
                .appendQueryParameter("cu", "INR")
                .build();


        addmoney = findViewById(R.id.addmoney);
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyUpiPayment.setPaymentStatusListener(wallet.this);
                Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
                upiPayIntent.setData(uri);

                // will always show a dialog to user to choose an app
                Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

                // check if intent resolves
                if(null != chooser.resolveActivity(getPackageManager())) {
                    startActivityForResult(chooser, PAY_REQUEST);
                    easyUpiPayment.startPayment();
                } else {
                    Toast.makeText(wallet.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAY_REQUEST){
            if(isInternetAvailabe(wallet.this)){
                if (data == null) {
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    String temp = "nothing";
                    Toast.makeText(this, "Transaction not complete", Toast.LENGTH_SHORT).show();
                }else {
                    String text = data.getStringExtra("response");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(text);
                    Toast.makeText(this, data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
                    upiPaymentCheck(text);
                }
            }

        }
    }

    void upiPaymentCheck(String data){
        String str = data;

        String payment_cancel = "";
        String status = "";
        String response[] = str.split("&");

        for (int i = 0; i < response.length; i++)
        {
            String equalStr[] = response[i].split("=");
            if(equalStr.length >= 2)
            {
                if (equalStr[0].toLowerCase().equals("Status".toLowerCase()))
                {
                    status = equalStr[1].toLowerCase();
                }
            }
            else
            {
                payment_cancel = "Payment cancelled";
            }
        }
        if(status.equals("success")){
            Toast.makeText(this, "Transaction Successfull", Toast.LENGTH_SHORT).show();
        }else if("Payment cancelled".equals(payment_cancel)){
            Toast.makeText(this, "payment cancelled by user", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Transaction failed", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isInternetAvailabe(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo.isConnected() && networkInfo.isConnectedOrConnecting() && networkInfo.isAvailable()){
                return true;
            }
        }
        return false;
    }


    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        Toast.makeText(this, "Transaction Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionSuccess() {
        Toast.makeText(this, "Transaction successfull", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionSubmitted() {
        Toast.makeText(this, "Transaction submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(this, "Transaction failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAppNotFound() {
        Toast.makeText(this, "Transaction app not found", Toast.LENGTH_SHORT).show();
    }
}