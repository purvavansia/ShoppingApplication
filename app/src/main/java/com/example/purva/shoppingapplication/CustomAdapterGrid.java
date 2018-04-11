package com.example.purva.shoppingapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by purva on 4/10/18.
 */

public class CustomAdapterGrid  extends RecyclerView.Adapter<CustomAdapterGrid.MyViewHolder>{
    ArrayList Necklacenames;
    ArrayList NecklaceImg;
    ArrayList NecklacePrice;
    Context context;
    public CustomAdapterGrid(Context context, ArrayList Necklacenames, ArrayList NecklaceImg, ArrayList NecklacePrice) {
        this.context = context;
        this.Necklacenames = Necklacenames;
        this.NecklaceImg = NecklaceImg;
        this.NecklacePrice = NecklacePrice;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_grid, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewName.setText(""+Necklacenames.get(position));
        holder.textViewPrice.setText(""+NecklacePrice.get(position));
        holder.imageView.setImageResource((Integer) NecklaceImg.get(position));
    }



    @Override
    public int getItemCount() {
        return Necklacenames.size();
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
