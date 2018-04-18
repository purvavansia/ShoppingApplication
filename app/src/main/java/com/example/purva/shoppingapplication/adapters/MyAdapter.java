package com.example.purva.shoppingapplication.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.purva.shoppingapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyAdapter extends PagerAdapter {

    // adapter for slider
    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;
 
    public MyAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);

    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
 
    @Override
    public int getCount() {
        return images.size();
    }
 
    @Override
    public Object instantiateItem(ViewGroup view, int position) {



        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Glide.with(context).load(images.get(position)).into(myImage);


        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}