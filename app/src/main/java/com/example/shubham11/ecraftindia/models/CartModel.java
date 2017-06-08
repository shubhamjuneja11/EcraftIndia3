package com.example.shubham11.ecraftindia.models;

/**
 * Created by shubhamjuneja11 on 8/6/17.
 */

public class CartModel {
    String name,sku,quantity,image;


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


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public CartModel(String name, String sku,String  quantity) {

        this.name = name;
        this.sku = sku;
        this.quantity = quantity;
    }
}
