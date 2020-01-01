package com.example.mobile.portaleventamikom.Filter;

public class EventFilter {
    private  String Deskripsi;
    private int ImageID;


    public EventFilter(String Deskripsi, int ImageID){
        this.Deskripsi= Deskripsi;
        this.ImageID =  ImageID;
    }
    public String getDeskripsi() {
        return Deskripsi;
    }

    public int getImageID() {
        return ImageID;
    }



}
