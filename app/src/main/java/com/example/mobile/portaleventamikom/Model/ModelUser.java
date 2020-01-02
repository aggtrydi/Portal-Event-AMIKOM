package com.example.mobile.portaleventamikom.Model;

public class ModelUser {

   private String nama,nim,email,prodi,search,image,cover,uid;

    public ModelUser() {
    }

    public ModelUser(String nama, String nim, String email, String prodi, String search, String image, String cover, String uid) {
        this.nama = nama;
        this.nim = nim;
        this.email = email;
        this.prodi = prodi;
        this.search = search;
        this.image = image;
        this.cover = cover;
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

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
