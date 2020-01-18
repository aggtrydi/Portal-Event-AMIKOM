package com.pea.mobile.portaleventamikom.model;

public class FavoritModel {
    String eId;
    String eJudulFav;
    String eDesFav;
    String eImagePoster;
    String eTimes;
    String uId;
    String fId;
    public FavoritModel(){

    }

    public FavoritModel(String eId, String eJudulFav, String eDesFav, String eImagePoster, String eTimes, String uId, String fId) {
        this.eId = eId;
        this.eJudulFav = eJudulFav;
        this.eDesFav = eDesFav;
        this.eImagePoster = eImagePoster;
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

    public String geteJudulFav() {
        return eJudulFav;
    }

    public void seteJudulFav(String eJudulFav) {
        this.eJudulFav = eJudulFav;
    }

    public String geteDesFav() {
        return eDesFav;
    }

    public void seteDesFav(String eDesFav) {
        this.eDesFav = eDesFav;
    }

    public String geteImagePoster() {
        return eImagePoster;
    }

    public void seteImagePoster(String eImagePoster) {
        this.eImagePoster = eImagePoster;
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
