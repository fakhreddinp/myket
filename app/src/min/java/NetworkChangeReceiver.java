package com.example.myketiap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                Log.i(TAG, "اتصال اینترنت برقرار شد");
                // می‌توانید خریدهای pending را چک کنید
            } else {
                Log.w(TAG, "اتصال اینترنت قطع شد");
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "خطا در بررسی وضعیت شبکه: " + e.getMessage());
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (Exception e) {
            Log.e(TAG, "خطا در بررسی اتصال: " + e.getMessage());
            return false;
        }
    }
}