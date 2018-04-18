package com.example.purva.shoppingapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.activity.OrderConfirmActivity;
import com.example.purva.shoppingapplication.pojo.Orders;
import com.example.purva.shoppingapplication.pojo.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by purva on 4/16/18.
 */

public class CustomAdapterOrders  extends RecyclerView.Adapter<CustomAdapterOrders.MyViewHolder> {


    // adapter for displaying all the orders placed
    ArrayList orderlist;
    SharedPreferences sharedPreferences;
    Context context;

    public CustomAdapterOrders(ArrayList orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
    }

    @Override
    public CustomAdapterOrders.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_orders, parent, false);
        CustomAdapterOrders.MyViewHolder vh = new CustomAdapterOrders.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomAdapterOrders.MyViewHolder holder, int position) {

        final Orders orders = (Orders) orderlist.get(position);

        holder.orderId.setText("ID: "+orders.getOrderid());
        holder.phone.setText("Phone: "+orders.getMobile());
        holder.placedon.setText("Placed On : "+orders.getPlacedon());
        holder.deliveryAddr.setText("Delivery Addr: "+orders.getDeliveryAddr());
        holder.name.setText("Order name: "+ orders.getName());


    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView orderId, phone, placedon,deliveryAddr,name;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewOrderName);
            orderId = itemView.findViewById(R.id.textViewOrderId);
            phone = itemView.findViewById(R.id.textViewOrderPhone);
            placedon = itemView.findViewById(R.id.textViewOrderPlacedOn);
            deliveryAddr = itemView.findViewById(R.id.textViewOrderDeliveryAddr);

        }
    }
}
