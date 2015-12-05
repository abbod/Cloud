package com.example.parth.cloud;

import android.media.Image;
import android.widget.ImageView;

public class Event {

    String eventName;
    String eventDescription;
    String eventInterests;
    //ImageView eventImage;

    public Event(String eventName,String eventDescription,String eventInterests){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventInterests = eventInterests;
        //this.eventImage = eventImage;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventInterests() {
        return eventInterests;
    }
    public void setEventInterests(String eventInterests) {
        this.eventInterests = eventInterests;
    }

//    public ImageView getEventImage(){
//        return eventImage;
//    }
//    public void setEventImage(ImageView eventImage){
//        this.eventImage = eventImage;
//    }
}
