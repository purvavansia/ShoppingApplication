package com.example.purva.shoppingapplication.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Created by purva on 4/17/18.
 */

public class CustomAdapterWishList extends BaseAdapter {

    // adapter for diaplaying the product details screen
    List<Products> productsList;
    Context context;
    LayoutInflater layoutInflater;
    SharedPreferences sharedPreferences;
    TextView name, price;
    ImageView image;

    public CustomAdapterWishList(Context context, ArrayList<Products> productlist) {
        this.context = context;
        this.productsList = productlist;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
         return productsList.size();
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
        Products products = productsList.get(position);
        name.setText("Name: "+products.getPname());
        price.setText("Price: "+products.getPrice());
        Picasso.with(context).load(products.getPimage()).into(image);
        return convertView;
    }


}

