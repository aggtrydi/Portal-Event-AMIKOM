package com.pea.mobile.portaleventamikom.model;

public class UserModel {
    String uId, uNim, uNama, uImage, uSandi, uEmail, uJurusan;

    public UserModel(){

    }

    public UserModel(String uId, String uNim, String uNama, String uImage, String uSandi, String uEmail, String uJurusan) {
        this.uId = uId;
        this.uNim = uNim;
        this.uNama = uNama;
        this.uImage = uImage;
        this.uSandi = uSandi;
        this.uEmail = uEmail;
        this.uJurusan = uJurusan;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuNim() {
        return uNim;
    }

    public void setuNim(String uNim) {
        this.uNim = uNim;
    }

    public String getuNama() {
        return uNama;
    }

    public void setuNama(String uNama) {
        this.uNama = uNama;
    }

    public String getuImage() {
        return uImage;
    }

    public void setuImage(String uImage) {
        this.uImage = uImage;
    }

    public String getuSandi() {
        return uSandi;
    }

    public void setuSandi(String uSandi) {
        this.uSandi = uSandi;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuJurusan() {
        return uJurusan;
    }

    public void setuJurusan(String uJurusan) {
        this.uJurusan = uJurusan;
    }


}
