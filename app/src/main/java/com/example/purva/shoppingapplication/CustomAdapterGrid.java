package com.example.purva.shoppingapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by purva on 4/10/18.
 */

public class CustomAdapterGrid  extends RecyclerView.Adapter<CustomAdapterGrid.MyViewHolder>{
    ArrayList productList;

    Context context;
    public CustomAdapterGrid(Context context, ArrayList productList) {
        this.context = context;
        this.productList = productList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_grid, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Products products = (Products) productList.get(position);

        holder.textViewName.setText(products.getPname());
        holder.textViewPrice.setText(products.getPrice());
        //holder.image.setImageResource((Integer) itemImages.get(position));
        Picasso.with(context).load(products.getPimage()).into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.nameGrid);
            textViewPrice = itemView.findViewById(R.id.priceGrid);
            imageView = itemView.findViewById(R.id.imageGrid);
        }
    }
}
