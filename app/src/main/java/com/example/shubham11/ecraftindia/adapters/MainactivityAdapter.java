package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.interfaces.RecyclerViewClickListener;
import com.example.shubham11.ecraftindia.models.SearchListModel;
import com.example.shubham11.ecraftindia.util.UtilityFile;

import java.util.ArrayList;

/**
 * Created by shubham11 on 29/5/17.
 */

public class MainactivityAdapter extends RecyclerView.Adapter<MainactivityAdapter.MyviewHolder> {
    ArrayList<SearchListModel> al;
    Context context;
    RecyclerViewClickListener itemlistener;
    RequestOptions options;
    String  rupee="\u20B9";
    public MainactivityAdapter(Context context, ArrayList<SearchListModel> al, RecyclerViewClickListener itemlistener){
        this.context=context;
        this.al=al;
        this.itemlistener=itemlistener;
        options=new RequestOptions();
        options.placeholder(R.drawable.tiger);

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
        //holder.price.setText("50 \\u20B9");

        Glide.with(context).load(UtilityFile.getSingleImage(model.getImageurl()))
                .thumbnail(0.5f).apply(options)
                .into(holder.image);
       try {
           holder.price.setText(rupee+String.valueOf(model.getCp()));
       }
       catch (Exception e){

       }
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView name,price,sku;
        public MyviewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.productimage);
            name=(TextView)itemView.findViewById(R.id.name);
            price=(TextView)itemView.findViewById(R.id.myprice);
            sku=(TextView)itemView.findViewById(R.id.sku);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemlistener.recycleritemClicked(v,this.getLayoutPosition());
        }
    }
}
