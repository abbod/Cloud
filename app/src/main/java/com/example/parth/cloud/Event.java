package com.example.parth.cloud;

import android.media.Image;
import android.widget.ImageView;

public class Event {

    String eventName;
    String eventDescription;
    String eventHandle;
    //String eventInterests;
    String eventImage;

    public Event(String eventName,String eventDescription, String eventHandle, String eventImage){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventHandle = eventHandle;
        //this.eventInterests = eventInterests;
        this.eventImage = eventImage;
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

    public String getEventHandle() {
        return eventHandle;
    }
    public void setEventHandle(String eventHandle) {
        this.eventHandle = eventHandle;
    }

    public String getEventImage() {
        return eventImage;
    }
    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

//    public String getEventInterests() {
//        return eventInterests;
//    }
//    public void setEventInterests(String eventInterests) {
//        this.eventInterests = eventInterests;
//    }

//    public ImageView getEventImage(){
//        return eventImage;
//    }
//    public void setEventImage(ImageView eventImage){
//        this.eventImage = eventImage;
//    }
}
