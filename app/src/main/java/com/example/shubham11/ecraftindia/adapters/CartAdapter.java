package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.models.CartModel;

import java.util.ArrayList;

/**
 * Created by shubhamjuneja11 on 8/6/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
ArrayList<CartModel> al;
    String rupee;
    Context context;
    public CartAdapter(Context context, ArrayList<CartModel>al){
        this.context=context;
        this.al=al;
        rupee=context.getString(R.string.Rs);
    }
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {
        CartModel model=al.get(position);
        holder.name.setText(model.getName());
        holder.sku.setText(model.getSku());
        holder.spinner.setSelection(model.getQuantity());
        holder.cp.setText(rupee+(model.getCp()*model.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,sku,cp;
        ImageView image;
        Spinner spinner;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            sku=(TextView)itemView.findViewById(R.id.sku);
            cp=(TextView)itemView.findViewById(R.id.cp);
            image=(ImageView)itemView.findViewById(R.id.image);
            spinner=(Spinner)itemView.findViewById(R.id.quantity);

        }
    }

}
