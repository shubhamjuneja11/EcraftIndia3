package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.models.ProductModel;

import java.util.ArrayList;

/**
 * Created by shubham11 on 29/5/17.
 */

public class MainactivityAdapter extends RecyclerView.Adapter<MainactivityAdapter.MyviewHolder> {
    ArrayList<ProductModel> al;
    Context context;
    public MainactivityAdapter(Context context,ArrayList<ProductModel>  al){
        this.context=context;
        this.al=al;
    }
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row,parent,false);
        return new MyviewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        public MyviewHolder(View itemView) {
            super(itemView);
        }
    }
}
