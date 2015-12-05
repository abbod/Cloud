package com.example.parth.cloud;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class  CardListActivity extends Activity implements SendReceive.AsyncResponse{

    int countUser = 0;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        HashMap<String,String> map = new HashMap<>();
        map.put("url", "http://e6cf3d72.ngrok.io/userdata");
        map.put("1", "OK");
        new SendReceive(CardListActivity.this).execute(map);
    }

    @Override
    public void processFinish(String response) {

        System.out.println("Reaching here: "+ response);
        try {
            JSONObject userJsonData = new JSONObject(response);
            jsonArray = userJsonData.getJSONArray("result");
            countUser = jsonArray.length();
            System.out.println(countUser);
            System.out.println(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i< countUser; i++) {
            Event event = null;
            try {
                event = new Event(jsonArray.getJSONObject(i).getString("name"),jsonArray.getJSONObject(i).getString("description"),jsonArray.getJSONObject(i).getString("interests"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Card card = new CustomCard(this,event);

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
}