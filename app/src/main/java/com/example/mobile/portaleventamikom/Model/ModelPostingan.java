package com.example.mobile.portaleventamikom.Model;

public class ModelPostingan {
    String pId,pJudul,pDescr,pImage,pTime,uid,nama,nim,email,dp;

    public ModelPostingan() {
    }

    public ModelPostingan(String pId, String pJudul, String pDescr, String pImage, String pTime, String uid, String nama, String nim, String email, String dp) {
        this.pId = pId;
        this.pJudul = pJudul;
        this.pDescr = pDescr;
        this.pImage = pImage;
        this.pTime = pTime;
        this.uid = uid;
        this.nama = nama;
        this.nim = nim;
        this.email = email;
        this.dp = dp;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpJudul() {
        return pJudul;
    }

    public void setpJudul(String pJudul) {
        this.pJudul = pJudul;
    }

    public String getpDescr() {
        return pDescr;
    }

    public void setpDescr(String pDescr) {
        this.pDescr = pDescr;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
}
