package com.example.myketiap;

import android.util.Log;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static final String SERVER_URL = "https://your-server.com/api/";
    
    public boolean verifyPurchase(String purchaseData, String signature) {
        try {
            URL url = new URL(SERVER_URL + "verify.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            String postData = "purchase_data=" + purchaseData + "&signature=" + signature;
            
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
            os.close();
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = br.readLine();
                br.close();
                
                return "VERIFIED".equals(response);
            }
        } catch (Exception e) {
            Log.e(TAG, "خطا در verifyPurchase: " + e.getMessage());
        }
        return false;
    }
    
    public boolean activatePremium(String token) {
        try {
            URL url = new URL(SERVER_URL + "activate.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            String postData = "token=" + token;
            
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
            os.close();
            
            int responseCode = conn.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            Log.e(TAG, "خطا در activatePremium: " + e.getMessage());
        }
        return false;
    }
}