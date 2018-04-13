package com.example.purva.shoppingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    List<Category> categoryList;
    Context context;
    SharedPreferences sharedPreferences;
    public CustomAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


   @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       final Category category = categoryList.get(position);
       Log.i("image",category.getImage());
        holder.name.setText(category.getName());
        //holder.image.setImageResource((Integer) itemImages.get(position));
       Picasso.with(context).load(category.getImage()).into(holder.image);

       holder.image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               sharedPreferences =  context.getSharedPreferences("myfile", Context.MODE_PRIVATE);
               String stored_api_key = sharedPreferences.getString("appapikey","");
               String stored_id = sharedPreferences.getString("userid","");
               String cid = category.getCid();

               //String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id=107&api_key="+stored_api_key+"&user_id="+stored_id;

               Intent i = new Intent(context,SubCategoryActivity.class);
               context.startActivity(i);

           }
       });


    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
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