package com.example.mobile.portaleventamikom.Filter;

public class EventFilter {
    private  String Deskripsi;
    private String Judul;
    private int ImageID;


    public EventFilter(String Judul, String Deskripsi, int ImageID){
        this.Judul = Judul;
        this.Deskripsi = Deskripsi;
        this.ImageID =  ImageID;
    }
    public String getJudul() {
        return Judul;
    }
    public String getDeskripsi() {
        return Deskripsi;
    }

    public int getImageID() {
        return ImageID;
    }



}
