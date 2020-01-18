package com.pea.mobile.portaleventamikom.model;

public class EventAndaModel {
    String eId, eJudul, eDeskripsi, eImage, uId;

    public EventAndaModel(){}
    public EventAndaModel(String eId, String eJudul, String eDeskripsi, String eImage, String uId) {
        this.eId = eId;
        this.eJudul = eJudul;
        this.eDeskripsi = eDeskripsi;
        this.eImage = eImage;
        this.uId = uId;
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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
