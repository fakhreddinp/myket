package com.example.myketiap;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyketIAP";
    private static final String SKU_PREMIUM = "premium_upgrade";
    
    private BillingManager billingManager;
    private TextView statusText;
    private Button buyButton;
    private boolean isPremium = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        statusText = findViewById(R.id.status_text);
        buyButton = findViewById(R.id.buy_button);
        
        billingManager = new BillingManager(this, new BillingManager.BillingUpdatesListener() {
            @Override
            public void onBillingSetupFinished(boolean success) {
                if (success) {
                    billingManager.queryPurchases();
                } else {
                    showMessage("خطا در اتصال به مایکت");
                }
            }
            
            @Override
            public void onPurchaseVerified(String sku, boolean success) {
                if (success && SKU_PREMIUM.equals(sku)) {
                    isPremium = true;
                    updateUI();
                    showMessage("خرید با موفقیت انجام شد!");
                }
            }
            
            @Override
            public void onQueryPurchasesFinished(boolean hasPremium) {
                isPremium = hasPremium;
                updateUI();
            }
        });
        
        buyButton.setOnClickListener(v -> {
            if (!isPremium) {
                billingManager.initiatePurchase(SKU_PREMIUM);
            }
        });
        
        updateUI();
    }
    
    private void updateUI() {
        runOnUiThread(() -> {
            if (isPremium) {
                statusText.setText("وضعیت: پریمیوم فعال ✓");
                buyButton.setEnabled(false);
                buyButton.setText("خرید شده");
            } else {
                statusText.setText("وضعیت: نسخه معمولی");
                buyButton.setEnabled(true);
                buyButton.setText("ارتقا به پریمیوم");
            }
        });
    }
    
    private void showMessage(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }
    
    @Override
    protected void onDestroy() {
        if (billingManager != null) {
            billingManager.destroy();
        }
        super.onDestroy();
    }
}