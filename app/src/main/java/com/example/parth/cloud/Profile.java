package com.example.parth.cloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ParthRajesh on 12/15/2015.
 */
public class Profile extends AppCompatActivity {
    TextView ClickedUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Intent profileIntent = getIntent();
        String clickedUserText = profileIntent.getStringExtra("ClickedUserData");
        JSONObject profile_json = null;
        try {
            profile_json = new JSONObject(clickedUserText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView name = (TextView) findViewById(R.id.profile_name);
        TextView description = (TextView) findViewById(R.id.profile_description);
        TextView link = (TextView) findViewById(R.id.profile_link);
        TextView handle = (TextView) findViewById(R.id.profile_handle);
        TextView location = (TextView) findViewById(R.id.profile_location);
        TextView friends = (TextView) findViewById(R.id.profile_friends_count);
        TextView followers = (TextView) findViewById(R.id.profile_followers_count);
        TextView interests = (TextView) findViewById(R.id.profile_interests);
        ImageView profile_image = (ImageView) findViewById(R.id.profile_image);

        try {
            name.setText(profile_json.getString("name"));
            description.setText(profile_json.getString("description"));
            link.setText(profile_json.getString("profile_url"));
            handle.setText(profile_json.getString("screen_name"));
            location.setText(profile_json.getString("location"));
            friends.setText(profile_json.getString("friends_count"));
            followers.setText(profile_json.getString("follower_count"));
            JSONObject interest_json = new JSONObject(profile_json.getString("interests"));
            Iterator<String> keys = interest_json.keys();
            String interest_string = "";
            while(keys.hasNext())
            {
                String key = (String)keys.next();
                interest_string += interest_json.get(key) + " ";
            }
            interests.setText("Interests :"+interest_string);
            Picasso.with(getApplicationContext()).load(profile_json.getString("profile_image_url")).resize(96, 96).centerCrop().into(profile_image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
