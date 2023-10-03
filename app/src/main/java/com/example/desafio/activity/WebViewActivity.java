package com.example.desafio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toolbar;

import com.example.desafio.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String url = bundle.getString("url");
            WebView webView = findViewById(R.id.webview);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            assert url != null;
            webView.loadUrl(url);
        }

    }
}