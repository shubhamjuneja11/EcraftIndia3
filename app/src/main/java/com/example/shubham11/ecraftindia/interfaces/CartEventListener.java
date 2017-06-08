package com.example.shubham11.ecraftindia.interfaces;

import com.example.shubham11.ecraftindia.models.CartModel;

/**
 * Created by shubhamjuneja11 on 8/6/17.
 */

public interface CartEventListener {
    public void shareProduct(CartModel model);
    public void removeProduct(String sku);
    public void changeQuantity(int position);
    public void getSelecteditem(int position);
}
