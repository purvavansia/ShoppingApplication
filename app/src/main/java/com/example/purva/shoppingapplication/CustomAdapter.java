package com.example.purva.shoppingapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList itemNames;
    ArrayList itemImages;
    Context context;
    public CustomAdapter(Context context, ArrayList itemNames, ArrayList itemImages) {
        this.context = context;
        this.itemNames = itemNames;
        this.itemImages = itemImages;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


   @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set the data in items
        holder.name.setText(""+itemNames.get(position));
        holder.image.setImageResource((Integer) itemImages.get(position));
       //Picasso.with(context).load(itemImages.getImage()).into(holder.image);
        // implement setOnClickListener event on item view.

    }
    @Override
    public int getItemCount() {
        return itemNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}