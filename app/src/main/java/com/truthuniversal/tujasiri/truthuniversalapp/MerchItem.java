package com.truthuniversal.tujasiri.truthuniversalapp;

import android.graphics.Bitmap;

/**
 * Created by tujasiri on 10/8/15.
 */
public class MerchItem {

    private int mt_item_num=0;
    private String mt_item_desc_long = "";
    private String mt_item_desc_short = "";
    private double mt_item_price = 0.0;
    private int mt_stock_qty = 0;
    private int mt_item_qty = 0;

    private String mt_image_id = "";
    private String mt_special_note = "";
    private String mt_item_link = "";
    private String mt_item_type = "";
    //private ImageView mt_imageview;
    private Bitmap mt_bitmap=Bitmap.createBitmap(90,90,Bitmap.Config.ARGB_8888);


    public MerchItem(){

        int mt_item_num=0;
        String mt_item_desc_long="";
        String mt_item_desc_short="";
        double mt_item_price=0.0;
        int mt_stock_qty=0;
        int mt_item_qty=0;

        String mt_image_id="";
        String mt_special_note="";
        String mt_item_link="";
        String mt_item_type="";
        //ImageView mt_imageview = null;
        Bitmap mt_bitmap = null;


        this.mt_item_num = mt_item_num;
        this.mt_item_desc_long = mt_item_desc_long;
        this.mt_item_desc_short = mt_item_desc_short;
        this.mt_item_price = mt_item_price;
        this.mt_stock_qty = mt_stock_qty;
        this.mt_stock_qty = mt_item_qty;
        this.mt_image_id = mt_image_id;
        this.mt_special_note = mt_special_note;
        this.mt_item_link = mt_item_link;
        this.mt_item_type = mt_item_type;
        this.mt_bitmap = mt_bitmap;
    }

    public int getMt_item_num(int i) {
        return mt_item_num;
    }

    public String getMt_item_desc_long() {
        return mt_item_desc_long;
    }

    public String getMt_item_desc_short() {
        return mt_item_desc_short;
    }

    public double getMt_item_price() {
        return mt_item_price;
    }

    public int getMt_stock_qty() {
        return mt_stock_qty;
    }

    public int getMt_item_qty() {
        return mt_item_qty;
    }

    public String getMt_image_id() {
        return mt_image_id;
    }

    public String getMt_special_note() {
        return mt_special_note;
    }

    public String getMt_item_link() {
        return mt_item_link;
    }

    public String getMt_item_type() {
        return mt_item_type;
    }

    //public ImageView getMt_imageview() {
    public Bitmap getMt_bitmap() {

            return mt_bitmap;
    }


    public void setMt_item_num(int mt_item_num) {
        this.mt_item_num = mt_item_num;
    }

    public void setMt_item_desc_long(String mt_item_desc_long) {
        this.mt_item_desc_long = mt_item_desc_long;
    }

    public void setMt_item_desc_short(String mt_item_desc_short) {
        this.mt_item_desc_short = mt_item_desc_short;
    }

    public void setMt_item_price(double mt_item_price) {
        this.mt_item_price = mt_item_price;
    }

    public void setMt_stock_qty(int mt_stock_qty) {
        this.mt_stock_qty = mt_stock_qty;
    }

    public void setMt_item_qty(int mt_item_qty) {
        this.mt_item_qty = mt_item_qty;
    }


    public void setMt_image_id(String mt_image_id) {
        this.mt_image_id = mt_image_id;
    }

    public void setMt_special_note(String mt_special_note) {
        this.mt_special_note = mt_special_note;
    }

    public void setMt_item_link(String mt_item_link) {
        this.mt_item_link = mt_item_link;
    }

    public void setMt_item_type(String mt_item_type) {
        this.mt_item_type = mt_item_type;
    }

    //public void setMt_imageview(ImageView mt_imageview) {
    public void setMt_bitmap(Bitmap mt_bitmap) {

            this.mt_bitmap = mt_bitmap;
    }

    public void dummyMethod(){}
}
