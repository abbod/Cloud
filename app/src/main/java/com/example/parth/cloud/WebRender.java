package com.example.parth.cloud;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by ParthRajesh on 12/5/2015.
 */
public class WebRender extends Activity{
    WebView Myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myintent= getIntent();
        String url = myintent.getStringExtra("TwitterPage");
        Intent myintent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(myintent2);
//        Myview = (WebView)findViewById(R.id.webpage1);
//        System.out.println("Reached till here: "+ url);
//        if(url != null)
//            Myview.loadUrl("https://www.google.com");
//        else
//            System.out.println("URL: "+url);
    }
}
