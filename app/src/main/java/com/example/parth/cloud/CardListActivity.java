package com.example.parth.cloud;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.HashMap;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        HashMap<String,String> map = new HashMap<>();
        map.put("url", "http://2a7b209c.ngrok.io/userdata");
        map.put("device", "mobile");
        new SendReceive(CardListActivity.this).execute(map);
    }

    @Override
    public void processFinish(String response) {

        System.out.println("Reaching here: " + response);
        try {
            JSONObject userJsonData = new JSONObject(response);
            jsonArray = userJsonData.getJSONArray("result");
            countUser = jsonArray.length();
            System.out.println(countUser);
            System.out.println(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Card> cards = new ArrayList<>();

        for (int i = 0; i< countUser; i++) {
            Event event = null;
            try {
                event = new Event(jsonArray.getJSONObject(i).getString("name"),jsonArray.getJSONObject(i).getString("description"), jsonArray.getJSONObject(i).getString("screen_name"), jsonArray.getJSONObject(i).getString("profile_image_url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Card card = new CustomCard(this,event);

            final int finalI = i;
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Intent profileIntent = new Intent(CardListActivity.this, Profile.class);
                    try {
                        profileIntent.putExtra("ClickedUserData", jsonArray.getJSONObject(finalI).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(profileIntent);
                }
            });

            //CardThumbnail thumb = new CardThumbnail(this);
            //thumb.setDrawableResource(listImages[i]);
            //card.addCardThumbnail(thumb);

            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, cards);

        CardListView listView = (CardListView) this.findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
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
                        "com.example.parth.cloud.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
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

