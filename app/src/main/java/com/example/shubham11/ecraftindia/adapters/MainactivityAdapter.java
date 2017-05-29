package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.models.ProductModel;
import com.example.shubham11.ecraftindia.models.SearchListModel;

import java.util.ArrayList;

/**
 * Created by shubham11 on 29/5/17.
 */

public class MainactivityAdapter extends RecyclerView.Adapter<MainactivityAdapter.MyviewHolder> {
    ArrayList<SearchListModel> al;
    Context context;
    public MainactivityAdapter(Context context,ArrayList<SearchListModel>  al){
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
        SearchListModel model=al.get(position);
        holder.name.setText(model.getName());
        holder.sku.setText(model.getSku());
//        holder.price.setText(model.getCp());

      /*  Glide.with(context).load(model.getImageurl())
                .thumbnail(0.5f)
                .into(holder.image);*/


    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,price,sku;
        public MyviewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.productimage);
            name=(TextView)itemView.findViewById(R.id.name);
            //price=(TextView)itemView.findViewById(R.id.price);
            sku=(TextView)itemView.findViewById(R.id.sku);
        }
    }
}
