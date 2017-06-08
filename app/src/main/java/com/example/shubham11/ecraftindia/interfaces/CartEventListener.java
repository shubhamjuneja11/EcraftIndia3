package com.example.shubham11.ecraftindia.interfaces;

/**
 * Created by shubhamjuneja11 on 8/6/17.
 */

public interface CartEventListener {
    public void viewProduct(String sku);
    public void removeProduct(String sku);
    public void changeQuantity(String sku,int quantity);
}
