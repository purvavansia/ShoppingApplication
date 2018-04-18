package com.example.purva.shoppingapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.activity.ProductDetailsActivity;
import com.example.purva.shoppingapplication.pojo.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by purva on 4/10/18.
 */

public class CustomAdapterGrid  extends RecyclerView.Adapter<CustomAdapterGrid.MyViewHolder>{


    // adapter for diaplaying the products
    ArrayList productList;
    SharedPreferences sharedPreferences;
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
        final Products products  = (Products) productList.get(position);

        holder.textViewName.setText("Name: "+products.getPname());
        holder.textViewPrice.setText("Price: Rs."+products.getPrice());
        //holder.image.setImageResource((Integer) itemImages.get(position));
        Picasso.with(context).load(products.getPimage()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences =  context.getSharedPreferences("myfile", Context.MODE_PRIVATE);
                String stored_api_key = sharedPreferences.getString("appapikey","");
                String stored_id = sharedPreferences.getString("userid","");
                String stored_subcid = sharedPreferences.getString("subcid","");
                String stored_cid = sharedPreferences.getString("cid","");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("product_id",products.getPid());
                editor.putString("product_name",products.getPname());
                editor.putString("product_price",products.getPrice());
                editor.putString("product_quantity",products.getQuantity());
                editor.putString("product_desc",products.getPdescription());
                editor.putString("product_image",products.getPimage());
                editor.commit();


                Intent i = new Intent(context,ProductDetailsActivity.class);
                i.putExtra("apikeyProduct",stored_api_key);
                i.putExtra("user_idProduct",stored_id);
                i.putExtra("pid",products.getPid());
                i.putExtra("subcid",stored_subcid);
                i.putExtra("cid",stored_cid);
                context.startActivity(i);
            }
        });

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
