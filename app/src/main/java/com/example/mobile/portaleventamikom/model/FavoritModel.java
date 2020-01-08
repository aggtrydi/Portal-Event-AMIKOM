package com.example.mobile.portaleventamikom.model;

public class FavoritModel {
    String eId;
    String eJudul;
    String eDeskripsi;
    String eImage;
    String eTimes;
    String uId;
    String fId;
    public FavoritModel(){

    }

    public FavoritModel(String eId, String eJudul, String eDeskripsi, String eImage, String eTimes, String uId, String fId) {
        this.eId = eId;
        this.eJudul = eJudul;
        this.eDeskripsi = eDeskripsi;
        this.eImage = eImage;
        this.eTimes = eTimes;
        this.uId = uId;
        this.fId = fId;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String geteJudul() {
        return eJudul;
    }

    public void seteJudul(String eJudul) {
        this.eJudul = eJudul;
    }

    public String geteDeskripsi() {
        return eDeskripsi;
    }

    public void seteDeskripsi(String eDeskripsi) {
        this.eDeskripsi = eDeskripsi;
    }

    public String geteImage() {
        return eImage;
    }

    public void seteImage(String eImage) {
        this.eImage = eImage;
    }

    public String geteTimes() {
        return eTimes;
    }

    public void seteTimes(String eTimes) {
        this.eTimes = eTimes;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }
}
