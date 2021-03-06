package com.truthuniversal.tujasiri.truthuniversalapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tujasiri on 6/14/2016.
 */
public class GoogleMusicAlbumItem {

        private String gma_album_id = "";
        private String gma_album_title = "";
        private String gma_album_image_url = "";
        private String gma_album_artist_name = "";
        private double gma_album_price = 0.0;
        //private GoogleMusicSongItem googleMusicSongItem = new GoogleMusicSongItem();
        private List<GoogleMusicSongItem> googleMusicSongItemList = new ArrayList<GoogleMusicSongItem>();


        public GoogleMusicAlbumItem(){
            String gma_album_id = "";
            String gma_album_title = "";
            String gma_album_image_url = "";
            String gma_album_artist_name = "";
            double gma_album_price = 0.0;
            //GoogleMusicSongItem googleMusicSongItem = new GoogleMusicSongItem();
            List<GoogleMusicSongItem> googleMusicSongItemList = new ArrayList<GoogleMusicSongItem>();


            this.gma_album_id = gma_album_id;
            this.gma_album_title = gma_album_title;
            this.gma_album_image_url = gma_album_image_url;
            this.gma_album_artist_name = gma_album_artist_name;
            this.gma_album_price = gma_album_price;
            this.googleMusicSongItemList = googleMusicSongItemList;
        }

        public String getGma_album_id() {
            return gma_album_id;
        }

        public String getGma_album_title() {
            return gma_album_title;
        }

        public String getGma_album_image_url() {
            return gma_album_image_url;
        }

        public String getGma_album_artist_name() {
            return gma_album_artist_name;
        }

        public List<GoogleMusicSongItem> getGoogleMusicSongItemList() {
            return googleMusicSongItemList;
        }



        public void setGma_album_id (String gma_album_id) {
            this.gma_album_id = gma_album_id;
        }
        public void setGma_album_title(String gma_album_title) {

            this.gma_album_title = gma_album_title;
        }

        public void setGma_album_image_url(String gma_album_image_url) {
            this.gma_album_image_url = gma_album_image_url;
        }

        public void setGma_album_artist_name(String gma_album_artist_name) {
            this.gma_album_artist_name = gma_album_artist_name;
        }

        public void setGma_album_price (double gma_album_price) {
            this.gma_album_price = gma_album_price;
        }

        public void setGoogleMusicSongItemList(List<GoogleMusicSongItem> googleMusicSongItemList) {
            this.googleMusicSongItemList = googleMusicSongItemList;
        }


}
