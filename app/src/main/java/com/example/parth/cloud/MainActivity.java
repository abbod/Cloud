package com.example.parth.cloud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.HashMap;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity implements SendReceive.AsyncResponse {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    int lastPage;
    TextView currentText,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout indContainer = (LinearLayout) findViewById(R.id.indicator_container);
        for(int x=0;x<4;x++){
            ImageView v = new ImageView(this);
            int px = (int)(4 * (getResources().getDisplayMetrics().densityDpi / 160));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            params.setMargins(px,0,px,0);
            v.setLayoutParams(params);
            v.setImageResource(R.drawable.indicator_inactive);
            indContainer.addView(v);
        }

        //Button
        Button spotify = (Button)findViewById(R.id.btn_spotify);
        spotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("url", "http://3bfb2be4.ngrok.io/signin");
                map.put("device", "mobile");
                new SendReceive(MainActivity.this).execute(map);
            }
        });

        title = (TextView) findViewById(R.id.title);
        title.setText(Html.fromHtml("<h1>Twintersts</h1>"));
        currentText = (TextView) findViewById(R.id.functions);
        currentText.setText(Html.fromHtml("Find users by Interests"));
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LinearLayout indContainer = (LinearLayout) findViewById(R.id.indicator_container);
                ((ImageView) indContainer.getChildAt(position)).setImageResource(R.drawable.indicator_active);
                ((ImageView) indContainer.getChildAt(lastPage)).setImageResource(R.drawable.indicator_inactive);
                lastPage = position;
                switch (position){
                    case 0:
                        currentText.setText(Html.fromHtml("Find users by Interests"));
                        break;

                    case 1:
                        currentText.setText(Html.fromHtml("See your interest graph"));
                        break;

                    case 2:
                        currentText.setText(Html.fromHtml("Start following common people"));
                        break;

                    case 3:
                        currentText.setText(Html.fromHtml("Have fun"));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void processFinish(String response) {
        System.out.println("Received response");
        System.out.println("Response: " + response);

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                "com.example.parth.cloud.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        //SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TwitterURL", response);
        editor.commit();



        Intent openLogin = new Intent("" + "com.example.parth.cloud.WEBRENDER");
        openLogin.putExtra("TwitterPage",response);
        System.out.println("Response: " + response);
        startActivity(openLogin);
    }
}
