package com.example.shubham11.ecraftindia.models;

/**
 * Created by shubhamjuneja11 on 8/6/17.
 */

public class CartModel {
    String name,sku;
    int cp,quantity;

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

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartModel(String name, String sku, int cp, int quantity) {

        this.name = name;
        this.sku = sku;
        this.cp = cp;
        this.quantity = quantity;
    }
}
