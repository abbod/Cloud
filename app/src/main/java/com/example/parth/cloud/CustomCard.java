package com.example.parth.cloud;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import it.gmariotti.cardslib.library.internal.Card;

public class CustomCard extends Card {

    protected TextView tvName;
    protected TextView tvDescription;
    protected TextView tvHandle;
    //protected TextView tvInterests;
    protected ImageView tvImage;
    protected Event event;


    /**
     * Constructor with a custom inner layout
     * @param context
     */
    public CustomCard(Context context, Event event) {
        this(context, R.layout.row_card);
        this.event=event;
    }

    /**
     *
     * @param context
     * @param innerLayout
     */
    public CustomCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    /**
     * Init
     */
    private void init(){

        //No Header

        //Set a OnClickListener listener
//        setOnClickListener(new OnCardClickListener() {
//            @Override
//            public void onClick(Card card, View view) {
//                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        tvName = (TextView) parent.findViewById(R.id.card_eventName);
        tvDescription = (TextView) parent.findViewById(R.id.card_eventDescription);
        tvHandle = (TextView) parent.findViewById(R.id.card_eventHandle);
        //tvInterests = (TextView) parent.findViewById(R.id.card_eventInterests);
        tvImage = (ImageView) parent.findViewById(R.id.card_eventImage);

        tvName.setText("Name: "+ event.getEventName());
        String modDescription = event.getEventDescription();
        if (modDescription.length() > 97)
        {
            modDescription = modDescription.substring(0,75);
            modDescription += "...";
        }
        tvDescription.setText("Description: "+ modDescription);
        tvHandle.setText("Twitter Handle: "+ event.getEventHandle());
        //tvInterests.setText(event.getEventInterests());
        //tvImage.setImageResource(R.mipmap.ic_launcher);
        Picasso.with(getContext()).load(event.getEventImage()).resize(70, 70).centerCrop().into(tvImage);
    }
}