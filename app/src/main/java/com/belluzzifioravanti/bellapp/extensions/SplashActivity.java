package com.belluzzifioravanti.bellapp.extensions;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.belluzzifioravanti.bellapp.ui.BellApp;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, BellApp.class));
        finish();
    }
}
