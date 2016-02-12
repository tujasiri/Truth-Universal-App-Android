package com.truthuniversal.tujasiri.truthuniversalapp;

import java.sql.Time;
import java.util.Date;

/**
 * Created by tujasiri on 12/14/15.
 */
public class NewsItem {

    private int news_id=0;
    private String news_title = "";
    private String news_subtitle = "";
    private String news_article = "";
    private String news_image_link = "";
    private String news_external_link = "";
    private Date news_expiration=null;
    private int news_type=0;
    private int news_event_id=0;
    private Time news_event_time=null;

    public NewsItem(){

        int news_id=0;
        String news_title = "";
        String news_subtitle = "";
        String news_article = "";
        String news_image_link = "";
        String news_external_link = "";
        Date news_expiration=null;
        int news_type=0;
        int news_event_id=0;
        Time news_event_time=null;

        this.news_id = news_id;
        this.news_title = news_title;
        this.news_subtitle = news_subtitle;
        this.news_article = news_article;
        this.news_image_link = news_image_link;
        this.news_external_link = news_external_link;
        this.news_expiration = news_expiration;
        this.news_type = news_type;
        this.news_event_id = news_event_id;
        this.news_event_time = news_event_time;


    }//end constructor

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_subtitle() {
        return news_subtitle;
    }

    public void setNews_subtitle(String news_subtitle) {
        this.news_subtitle = news_subtitle;
    }

    public String getNews_article() {
        return news_article;
    }

    public void setNews_article(String news_article) {
        this.news_article = news_article;
    }

    public String getNews_image_link() {
        return news_image_link;
    }

    public void setNews_image_link(String news_image_link) {
        this.news_image_link = news_image_link;
    }

    public String getNews_external_link() {
        return news_external_link;
    }

    public void setNews_external_link(String news_external_link) {
        this.news_external_link = news_external_link;
    }

    public Date getNews_expiration() {
        return news_expiration;
    }

    public void setNews_expiration(Date news_expiration) {
        this.news_expiration = news_expiration;
    }

    public int getNews_type() {
        return news_type;
    }

    public void setNews_type(int news_type) {
        this.news_type = news_type;
    }

    public int getNews_event_id() {
        return news_event_id;
    }

    public void setNews_event_id(int news_event_id) {
        this.news_event_id = news_event_id;
    }

    public Time getNews_event_time() {
        return news_event_time;
    }

    public void setNews_event_time(Time news_event_time) {
        this.news_event_time = news_event_time;
    }
}
