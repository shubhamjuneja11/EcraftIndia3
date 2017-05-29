package com.example.shubham11.ecraftindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham11.ecraftindia.R;
import com.example.shubham11.ecraftindia.models.SearchListModel;

import java.util.ArrayList;

/**
 * Created by shubham11 on 28/5/17.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {
    ArrayList<SearchListModel> al;
    Context context;
    public SearchListAdapter(Context context,ArrayList<SearchListModel> al){
        this.al=al;
        this.context=context;
    }
    @Override
    public SearchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchlist_row,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(SearchListAdapter.MyViewHolder holder, int position) {
        SearchListModel model=al.get(position);
        holder.name.setText(model.getName());
        holder.sku.setText(model.getSku());
        holder.cp.setText(String.valueOf(model.getCp()));
        holder.sp.setText(String.valueOf(model.getSp()));

      /*  Glide.with(context).load(model.getImageurl())
                .thumbnail(0.5f)
                .into(holder.image);*/
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView cp,sp,name,sku;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            cp=(TextView)itemView.findViewById(R.id.cp);
            sp=(TextView)itemView.findViewById(R.id.sp);
            name=(TextView)itemView.findViewById(R.id.name);
            sku=(TextView)itemView.findViewById(R.id.sku);
        }
    }
}
