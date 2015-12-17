package com.example.parth.cloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity implements SendReceive.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FancyButton spotify = (FancyButton)findViewById(R.id.btn_spotify);
        spotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("url", "http://affa08e4.ngrok.io/signin");
                map.put("1", "OK");
                new SendReceive(MainActivity.this).execute(map);
            }
        });
    }

    @Override
    public void processFinish(String response) {
        System.out.println("Received response");
        System.out.println("Response: " + response);
        Intent openLogin = new Intent("" + "com.example.parth.cloud.WEBRENDER");
        openLogin.putExtra("TwitterPage",response);
        System.out.println("Response: " + response);
        startActivity(openLogin);
    }
}
