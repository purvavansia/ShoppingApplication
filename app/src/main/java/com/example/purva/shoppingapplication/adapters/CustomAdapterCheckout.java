package com.example.purva.shoppingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.pojo.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by purva on 4/14/18.
 */

public class CustomAdapterCheckout  extends BaseAdapter {


    // adapter for displaying the cart details
    LayoutInflater layoutInflater;
    TextView name, price;
    ImageView image;
    List<Products> productList;
    Context context;
    public CustomAdapterCheckout(Context context, ArrayList<Products> productList) {
        this.context = context;
        this.productList = productList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.checkout_row,null);
        name = (TextView) convertView.findViewById(R.id.checkoutName);
        image = (ImageView) convertView.findViewById(R.id.checkoutImage);
        price = convertView.findViewById(R.id.checkoutPrice);
        Products products = productList.get(position);
        name.setText("Name: "+products.getPname());
        price.setText("Price: "+products.getPrice());
        Picasso.with(context).load(products.getPimage()).into(image);
        return convertView;
    }



}
