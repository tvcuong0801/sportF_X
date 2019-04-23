package com.example.tvcuo.sportf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class tintuc_Activity extends AppCompatActivity {
WebView webView;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tintuc);
        webView=findViewById(R.id.webViewBongDa);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new MyWebViewClient());
        goUrl();
    }

    public class MyWebViewClient extends WebViewClient {

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return super.shouldOverrideUrlLoading(view, url);
//        }

    }
    @SuppressLint("SetJavaScriptEnabled")
    private void goUrl()  {
        String url = "http://bongdaplus.vn/";
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
}
