package com.example.mobile.portaleventamikom.filter;

public class EventFilter {
    private String eJudul;
    private String eDeskripsi;
    private int eImage;

    public EventFilter(String eJudul, String eDeskripsi, int eImage) {
        this.eJudul = eJudul;
        this.eDeskripsi = eDeskripsi;
        this.eImage = eImage;
    }

    public String geteJudul() {
        return eJudul;
    }

    public String geteDeskripsi() {
        return eDeskripsi;
    }

    public int geteImage() {
        return eImage;
    }
}
