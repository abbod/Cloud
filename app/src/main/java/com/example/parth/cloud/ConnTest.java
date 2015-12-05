package com.example.parth.cloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by ParthRajesh on 12/4/2015.
 */
public class ConnTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conntest);

        Button testBtn = (Button)findViewById(R.id.connTestButtton);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map = new HashMap<>();
                map.put("url","http://e6cf3d72.ngrok.io/android");
                map.put("1","one");
                map.put("2","two");
                map.put("3", "three");
                //SendReceive sr = new SendReceive();
                //new SendReceive().execute(map);

                new SendReceive(new SendReceive.AsyncResponse(){

                    @Override
                    public void processFinish(String response){
                        System.out.println("Received response");
                        System.out.println("Response: " + response);
                    }
                }).execute(map);
            }
        });
    }
}