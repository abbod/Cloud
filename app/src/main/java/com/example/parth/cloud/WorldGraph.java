package com.example.parth.cloud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.HashMap;

/**
 * Created by ParthRajesh on 12/17/2015.
 */
public class WorldGraph extends AppCompatActivity implements SendReceive.AsyncResponse{
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
        String url = sharedPref.getString("UserId", "null");
        mWebView.loadUrl("http://8f23a109.ngrok.io/twitterGraph?device=web&user_id=" + url);


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                loadingFinished = false;
                RelativeLayout rL = (RelativeLayout) findViewById(R.id.relative_layout);
                ProgressBar pB = (ProgressBar) findViewById(R.id.progressBarForGraph);
                rL.setVisibility(View.VISIBLE);
                pB.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    RelativeLayout rL = (RelativeLayout) findViewById(R.id.relative_layout);
                    ProgressBar pB = (ProgressBar) findViewById(R.id.progressBarForGraph);
                    mWebView.setVisibility(View.VISIBLE);
                    rL.setVisibility(View.GONE);
                    pB.setVisibility(View.GONE);
                } else {
                    redirect = false;
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_graph, menu);
        return true;
    }


    boolean loadingFinished = true;
    boolean redirect = false;

//    mWebView.setWebViewClient(new WebViewClient() {
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
//            if (!loadingFinished) {
//                redirect = true;
//            }
//
//            loadingFinished = false;
//            mWebView.loadUrl(urlNewString);
//            return true;
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url) {
//            loadingFinished = false;
//            //SHOW LOADING IF IT ISNT ALREADY VISIBLE
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            if(!redirect){
//                loadingFinished = true;
//            }
//
//            if(loadingFinished && !redirect){
//                //HIDE LOADING IT HAS FINISHED
//            } else{
//                redirect = false;
//            }
//
//        }
//    });


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dashboard:
                SharedPreferences sharedPrefuser = this.getSharedPreferences(
                        "com.example.parth.cloud.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
                String currentUser = sharedPrefuser.getString("User", "null");
                Intent profileIntent = new Intent(WorldGraph.this, Profile.class);
                profileIntent.putExtra("ClickedUserData", currentUser);
                startActivity(profileIntent);
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
            case R.id.logout:
                SharedPreferences sharedPref = this.getSharedPreferences(
                        "com.example.parth.cloud.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
                final String user_id = sharedPref.getString("UserId", "null");
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                HashMap<String,String> map = new HashMap<>();
                                map.put("url", "http://8f23a109.ngrok.io/logout");
                                map.put("device", "mobile");
                                map.put("user_id", user_id);
                                new SendReceive(WorldGraph.this).execute(map);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void processFinish(String response)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
