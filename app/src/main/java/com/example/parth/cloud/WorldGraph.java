package com.example.parth.cloud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ParthRajesh on 12/17/2015.
 */
public class WorldGraph extends AppCompatActivity {
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usergraph);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        SharedPreferences sharedPref = this.getSharedPreferences(
                "com.example.parth.cloud.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
        String url = sharedPref.getString("UserId","null");
        mWebView.loadUrl("http://2a7b209c.ngrok.io/twitterGraph?device=web&user_id="+url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dashboard:
                //Toast.makeText(this,"Works",Toast.LENGTH_LONG).show();
                SharedPreferences sharedPref = this.getSharedPreferences(
                        "com.example.parth.cloud.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
                String url = sharedPref.getString("TwitterURL","null");
                //Toast.makeText(this,url,Toast.LENGTH_LONG).show();
                Intent myintent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(myintent2);
                //Intent first = new Intent("" + "com.example.parth.cloud.VIEW");
                //startActivity(first);
                return true;
            case R.id.user_graph:
                //Toast.makeText(this,"Works",Toast.LENGTH_LONG).show();
                Intent second = new Intent("" + "com.example.parth.cloud.USERGRAPH");
                startActivity(second);
                return true;
            case R.id.overall_graph:
                //Toast.makeText(this,"Works",Toast.LENGTH_LONG).show();
                Intent third = new Intent("" + "com.example.parth.cloud.WORLDGRAPH");
                startActivity(third);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
