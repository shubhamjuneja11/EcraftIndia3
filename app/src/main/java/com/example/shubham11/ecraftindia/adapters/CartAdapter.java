package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.interfaces.CartEventListener;
import com.example.shubham11.ecraftindia.models.CartModel;

import java.util.ArrayList;

/**
 * Created by shubhamjuneja11 on 8/6/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
ArrayList<CartModel> al;
    String rupee;
    Context context;
    CartEventListener listener;
    ImageView iv;
    public CartAdapter(Context context, ArrayList<CartModel>al,CartEventListener listener){
        this.context=context;
        this.al=al;
        rupee=context.getString(R.string.Rs);
        this.listener=listener;
    }
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {
        CartModel model=al.get(position);
        holder.sku.setText(model.getSku());
        holder.comment.setText(model.getQuantity());


    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView sku;
        ImageView image;
        Button view,remove;
        TextView comment;
        public MyViewHolder(View itemView) {
            super(itemView);

            sku=(TextView)itemView.findViewById(R.id.sku);
            image=(ImageView)itemView.findViewById(R.id.image);
            comment=(TextView)itemView.findViewById(R.id.comment);
            view=(Button)itemView.findViewById(R.id.share);
            remove=(Button)itemView.findViewById(R.id.remove);
            iv=image;

            /***************listeners**********************/

            view.setOnClickListener(this);
            remove.setOnClickListener(this);
            comment.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.getSelecteditem(getAdapterPosition());
            if(v.getId()==R.id.share)
            {Log.e("yup","yup");
                View view = v.getRootView();
                String sku=((TextView)view.findViewById(R.id.sku)).getText().toString();
                String comment=((TextView)view.findViewById(R.id.comment)).getText().toString();
                iv = (ImageView) view.findViewById(R.id.image);
                listener.shareProduct(al.get(getAdapterPosition()),iv,sku,comment);
            }
            else if(v.getId()==R.id.remove)
                listener.removeProduct(al.get(this.getAdapterPosition()).getSku());
            else if(v.getId()==R.id.comment)
                listener.changeQuantity(getAdapterPosition());

        }


    }

}
