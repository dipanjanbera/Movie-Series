package com.dipanjan.app.ytsyifyhdmovietorrentdownloader.activity;

/**
 * Created by LENOVO on 19-08-2018.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import info.dipanjan.app.R;

/**
 * Created by TheFinestArtist on 3/11/16.
 */
public class WebViewActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(
                "http://docs.google.com/gview?embedded=true&url=uet.vnu.edu.vn/~chauttm/e-books/java/Effective.Java.2nd.Edition.May.2008.3000th.Release.pdf");
    }
}