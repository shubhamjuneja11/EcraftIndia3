package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.models.CartModel;

import java.util.ArrayList;

/**
 * Created by shubhamjuneja11 on 8/6/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
ArrayList<CartModel> al;
    Context context;
    public CartAdapter(Context context, ArrayList<CartModel>al){
        this.context=context;
        this.al=al;
    }
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
