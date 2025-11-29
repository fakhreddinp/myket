package com.example.myketiap;

import android.content.Context;
import android.util.Log;
import com.myket.billing.IabHelper;
import com.myket.billing.IabResult;
import com.myket.billing.Inventory;
import com.myket.billing.Purchase;

public class BillingManager {
    private static final String TAG = "BillingManager";
    
    private IabHelper iabHelper;
    private Context context;
    private BillingUpdatesListener listener;
    private NetworkManager networkManager;
    
    public interface BillingUpdatesListener {
        void onBillingSetupFinished(boolean success);
        void onPurchaseVerified(String sku, boolean success);
        void onQueryPurchasesFinished(boolean hasPremium);
    }
    
    public BillingManager(Context context, BillingUpdatesListener listener) {
        this.context = context;
        this.listener = listener;
        this.networkManager = new NetworkManager();
        setupBilling();
    }
    
    private void setupBilling() {
        iabHelper = new IabHelper(context, BuildConfig.IAB_PUBLIC_KEY);
        iabHelper.enableDebugLogging(true);
        
        iabHelper.startSetup(result -> {
            if (result.isSuccess()) {
                Log.d(TAG, "اتصال به مایکت موفقیت‌آمیز بود");
                listener.onBillingSetupFinished(true);
            } else {
                Log.e(TAG, "خطا در اتصال به مایکت: " + result.getMessage());
                listener.onBillingSetupFinished(false);
            }
        });
    }
    
    public void queryPurchases() {
        try {
            iabHelper.queryInventoryAsync((result, inventory) -> {
                if (result.isFailure()) {
                    Log.e(TAG, "خطا در بررسی خریدها: " + result.getMessage());
                    return;
                }
                
                boolean hasPremium = inventory.hasPurchase("premium_upgrade");
                listener.onQueryPurchasesFinished(hasPremium);
            });
        } catch (Exception e) {
            Log.e(TAG, "خطا در queryPurchases: " + e.getMessage());
        }
    }
    
    public void initiatePurchase(String sku) {
        try {
            String payload = "user_" + System.currentTimeMillis();
            
            iabHelper.launchPurchaseFlow(
                (android.app.Activity) context,
                sku,
                10001,
                (result, purchase) -> {
                    if (result.isFailure()) {
                        Log.e(TAG, "خطا در خرید: " + result.getMessage());
                        return;
                    }
                    
                    if (purchase != null) {
                        verifyPurchaseWithServer(purchase);
                    }
                },
                payload
            );
        } catch (Exception e) {
            Log.e(TAG, "خطا در initiatePurchase: " + e.getMessage());
        }
    }
    
    private void verifyPurchaseWithServer(Purchase purchase) {
        new Thread(() -> {
            boolean verified = networkManager.verifyPurchase(
                purchase.getOriginalJson(),
                purchase.getSignature()
            );
            
            if (verified) {
                activatePremiumOnServer(purchase.getToken());
            } else {
                Log.e(TAG, "تایید خرید با سرور ناموفق بود");
            }
        }).start();
    }
    
    private void activatePremiumOnServer(String token) {
        boolean activated = networkManager.activatePremium(token);
        
        if (activated) {
            listener.onPurchaseVerified("premium_upgrade", true);
        } else {
            Log.e(TAG, "فعال‌سازی پریمیوم در سرور ناموفق بود");
        }
    }
    
    public void destroy() {
        if (iabHelper != null) {
            iabHelper.disposeWhenFinished();
            iabHelper = null;
        }
    }
}