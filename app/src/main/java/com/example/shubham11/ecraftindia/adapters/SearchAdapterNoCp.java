package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.interfaces.RecyclerViewClickListener;
import com.example.shubham11.ecraftindia.models.SearchListModel;
import com.example.shubham11.ecraftindia.util.UtilityFile;

import java.util.ArrayList;

/**
 * Created by shubham11 on 28/5/17.
 */

public class SearchAdapterNoCp extends RecyclerView.Adapter<SearchAdapterNoCp.MyViewHolder> {
    ArrayList<SearchListModel> al;
    Context context;
    RecyclerViewClickListener listener;
    public SearchAdapterNoCp(Context context,ArrayList<SearchListModel> al,RecyclerViewClickListener listener){
        this.al=al;
        this.context=context;
        this.listener=listener;
    }
    @Override
    public SearchAdapterNoCp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchlistrow_nocp,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(SearchAdapterNoCp.MyViewHolder holder, int position) {
        SearchListModel model=al.get(position);
        holder.name.setText(model.getName());
        holder.sku.setText(model.getSku());
        String sp="S.P.      \u20B9 "+String.valueOf(model.getSp());
        holder.sp.setText(sp);

        Glide.with(context).load(UtilityFile.getSingleImage(model.getImageurl()))
                .thumbnail(0.5f)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView cp,sp,name,sku;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            sp=(TextView)itemView.findViewById(R.id.sp);
            name=(TextView)itemView.findViewById(R.id.name);
            sku=(TextView)itemView.findViewById(R.id.sku);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.recycleritemClicked(v,this.getLayoutPosition());
        }
    }
}
