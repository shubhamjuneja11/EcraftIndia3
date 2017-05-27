package com.example.shubham11.ecraftindia.models;

/**
 * Created by shubham11 on 28/5/17.
 */

public class SearchListModel {
    private String imageurl,name,sku;
    private int sp;

    public SearchListModel(String imageurl, String name, String sku, int sp, int cp) {
        this.imageurl = imageurl;
        this.name = name;
        this.sku = sku;
        this.sp = sp;
        this.cp = cp;
    }

    public String getImageurl() {

        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    private int cp;
}
