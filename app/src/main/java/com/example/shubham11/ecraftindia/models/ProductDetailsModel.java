package com.example.shubham11.ecraftindia.models;

import java.io.Serializable;

/**
 * Created by shubhamjuneja11 on 17/6/17.
 */

public class ProductDetailsModel implements Serializable {
    String sku;
    String msku;
    String name;
    String primary_category;
    String category;
    String cp;
    String mrp;
    String sp;
    String material;
    String color;
    String size;
    String inventory;

    public ProductDetailsModel(String sku, String msku, String name, String primary_category, String category, String cp, String mrp, String sp, String material, String color, String size, String inventory, String inventory_type, String comment, String commentBy) {
        this.sku = sku;
        this.msku = msku;
        this.name = name;
        this.primary_category = primary_category;
        this.category = category;
        this.cp = cp;
        this.mrp = mrp;
        this.sp = sp;
        this.material = material;
        this.color = color;
        this.size = size;
        this.inventory = inventory;
        this.inventory_type = inventory_type;
        this.comment = comment;
        this.commentBy = commentBy;
    }

    String inventory_type;
    String comment;

    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    String commentBy;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimary_category() {
        return primary_category;
    }

    public void setPrimary_category(String primary_category) {
        this.primary_category = primary_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getInventory_type() {
        return inventory_type;
    }

    public void setInventory_type(String inventory_type) {
        this.inventory_type = inventory_type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
