package com.example.parth.cloud;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class  CardListActivity extends AppCompatActivity implements SendReceive.AsyncResponse{

    int countUser = 0;
    JSONArray jsonArray;
    JSONObject currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String strdata = i.getDataString();
        System.out.println("String data "+strdata);
        setContentView(R.layout.activity_list);

        HashMap<String,String> map = new HashMap<>();
        map.put("url", strdata);
        map.put("device", "mobile");
        new SendReceive(CardListActivity.this).execute(map);
    }

    @Override
    public void processFinish(String response) {
//        SharedPreferences sharedPref = this.getSharedPreferences(
//                "com.example.parth.cloud.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("Response", universalResponse);
//        editor.commit();
        try {
            JSONObject userJsonData = new JSONObject(response);
            String user_id = userJsonData.getString("user_id");
            Context context = this;
            SharedPreferences sharedPref = context.getSharedPreferences(
                    "com.example.parth.cloud.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("UserId", user_id);
            editor.commit();

            ArrayList<Card> cards = new ArrayList<>();
            currentUser = userJsonData.getJSONObject("user");
            JSONObject matchingUsers = userJsonData.getJSONObject("matchingUsers");
            Iterator<String> keys = matchingUsers.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (matchingUsers.get(key) instanceof JSONObject) {
                    countUser++;
                    Event event = null;
                    JSONObject everyUser = (JSONObject) matchingUsers.get(key);
                    final JSONObject userDetails = (JSONObject) everyUser.get("userDetails");
                    JSONObject commonInterest = (JSONObject) everyUser.get("commonInterests");
                    String name = userDetails.getString("name");
                    String imageURL = userDetails.getString("profile_image_url");
                    String screenName = userDetails.getString("screen_name");
                    StringBuffer comInterest = new StringBuffer();
                    Iterator<String> innerkeys = commonInterest.keys();
                    while (innerkeys.hasNext()) {
                        String innerkey = (String) innerkeys.next();
                        comInterest.append(commonInterest.get(innerkey) + ", ");
                    }
                    comInterest.deleteCharAt(comInterest.length() - 1);
                    String comInterestString = comInterest.toString();
                    event = new Event(name, comInterestString, screenName, imageURL);
                    Card card = new CustomCard(this, event);
                    cards.add(card);

                    card.setOnClickListener(new Card.OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Intent profileIntent = new Intent(CardListActivity.this, Profile.class);
                            profileIntent.putExtra("ClickedUserData", userDetails.toString());
                            startActivity(profileIntent);
                        }
                    });

                }
            }
            CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, cards);

            CardListView listView = (CardListView) this.findViewById(R.id.myList);
            if (listView != null) {
                listView.setAdapter(mCardArrayAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
//            for (int i = 0; i < countUser; i++) {
//                final int finalI = i;
//
//            }
//

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
                //SharedPreferences sharedPref = this.getSharedPreferences(
                //        "com.example.parth.cloud.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
                //String url = sharedPref.getString("TwitterURL","null");
                //Toast.makeText(this,url,Toast.LENGTH_LONG).show();
                //Intent myintent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                //startActivity(myintent2);
                //Intent first = new Intent("" + "com.example.parth.cloud.VIEW");
                //startActivity(first);
                Intent profileIntent = new Intent(CardListActivity.this, Profile.class);
                profileIntent.putExtra("ClickedUserData", currentUser.toString());
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

