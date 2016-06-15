package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 6/14/2016.
 */
public class GoogleMusicSongItem {

    private String gs_song_id = "";
    private String gs_song_title = "";
    private double gs_song_price = 0.0;


    public GoogleMusicSongItem(){
        String gs_song_id = "";
        String gs_song_title = "";
        double gs_song_price = 0.0;


        this.gs_song_id = gs_song_id;
        this.gs_song_title = gs_song_title;
        this.gs_song_price = gs_song_price;
    }

    public String getGs_song_id() {
        return gs_song_id;
    }

    public String getGs_song_title() {
        return gs_song_title;
    }

    public double getGs_song_price() {
        return gs_song_price;
    }




    public void setGs_song_id(String gs_song_id) {
        this.gs_song_id = gs_song_id;
    }

    public void setGs_song_title(String gs_song_title) {
        this.gs_song_title = gs_song_title;
    }

    public void setGs_song_price(double gs_song_price) {
        this.gs_song_price = gs_song_price;
    }


}
