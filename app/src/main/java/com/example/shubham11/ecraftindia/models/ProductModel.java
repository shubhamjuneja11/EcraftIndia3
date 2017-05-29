package com.example.shubham11.ecraftindia.models;

/**
 * Created by shubham11 on 29/5/17.
 */

public class ProductModel {
    private String imageurl,name,sku,product_color,material,category;
    private int sp,cp,mrp;

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

    public String getProduct_color() {
        return product_color;
    }

    public void setProduct_color(String product_color) {
        this.product_color = product_color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public ProductModel(String imageurl, String name, String sku, String product_color, String material, String category, int sp, int cp, int mrp) {

        this.imageurl = imageurl;
        this.name = name;
        this.sku = sku;
        this.product_color = product_color;
        this.material = material;
        this.category = category;
        this.sp = sp;
        this.cp = cp;
        this.mrp = mrp;
    }
}
