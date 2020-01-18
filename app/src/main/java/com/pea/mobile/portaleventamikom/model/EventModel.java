package com.pea.mobile.portaleventamikom.model;

public class EventModel {
    String eId;
    String eJudul;
    String eDeskripsi;
    String eImage;
    String eTimes;
    String uId;
    String uNama;
    String uNim;
    String uEmail;
    String uImage;

    public EventModel(){

    }

    public EventModel(String eId, String eJudul, String eDeskripsi, String eImage, String eTimes, String uId, String uNama, String uNim, String uEmail, String uImage) {
        this.eId = eId;
        this.eJudul = eJudul;
        this.eDeskripsi = eDeskripsi;
        this.eImage = eImage;
        this.eTimes = eTimes;
        this.uId = uId;
        this.uNama = uNama;
        this.uNim = uNim;
        this.uEmail = uEmail;
        this.uImage = uImage;
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

    public String getuNama() {
        return uNama;
    }

    public void setuNama(String uNama) {
        this.uNama = uNama;
    }

    public String getuNim() {
        return uNim;
    }

    public void setuNim(String uNim) {
        this.uNim = uNim;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuImage() {
        return uImage;
    }

    public void setuImage(String uImage) {
        this.uImage = uImage;
    }
}
