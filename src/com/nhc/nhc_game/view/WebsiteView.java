package com.nhc.nhc_game.view;

import com.nhc.nhc_game.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebsiteView extends Activity{

	@SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.website);
       
        WebView StateSite = (WebView) findViewById(R.id.website_webview);
        StateSite.loadUrl("http://128.6.29.222:8080");
        //StateSite.loadUrl("http://www.example.com");
    }
}
