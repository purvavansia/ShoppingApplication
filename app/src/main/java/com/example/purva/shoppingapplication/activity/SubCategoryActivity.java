package com.example.purva.shoppingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.shoppingapplication.pojo.Category;
import com.example.purva.shoppingapplication.adapters.CustomAdapterSubCategory;
import com.example.purva.shoppingapplication.adapters.MyAdapter;
import com.example.purva.shoppingapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SubCategoryActivity extends AppCompatActivity
        {

    ArrayList<Category> subcategories;
    Category subcategory;
    String sliderImage;
    public List<String> sliderList;
    private static ViewPager mPager;
    private static int currentPage = 0;
    SharedPreferences sharedPreferences;
    Button cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cart = findViewById(R.id.buttonCartSubCategory);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubCategoryActivity.this,CheckOutActivity.class);
                startActivity(i);
            }
        });
//recycler view for displaying sub categorys
        String apiKey = getIntent().getExtras().getString("apikeySub");
        String user_id = getIntent().getExtras().getString("user_idSub");
        String cid = getIntent().getExtras().getString("cid");

        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id="+cid+"&api_key="+apiKey+"&user_id="+user_id;
        //Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        subcategories = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("subcategory");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject myCategory = jsonArray.getJSONObject(i);
                        String subimage = myCategory.getString("scimageurl");
                        String subname = myCategory.getString("scname");
                        String subcid = myCategory.getString("scid");
                        String subcdiscription = myCategory.getString("scdiscription");
                        //Toast.makeText(MainActivity.this,image,Toast.LENGTH_SHORT).show();

                        subcategory = new Category(subname,subimage,subcdiscription,subcid);
                        subcategories.add(subcategory);

                    }
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSub_category);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
                    CustomAdapterSubCategory customAdapter = new CustomAdapterSubCategory(SubCategoryActivity.this, subcategories);
                    customAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(customAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SubCategoryActivity.this);
        requestQueue.add(stringRequest);

    }




//Slider for subcategory activity
    private void init() throws InterruptedException {

        sharedPreferences =  getSharedPreferences("myfile", Context.MODE_PRIVATE);
        //final Integer[] SLIDER= {R.drawable.slider_image1,R.drawable.slider_image2,R.drawable.slider_image3,R.drawable.slider_image4,R.drawable.slider_image5};

        String stored_api_key = sharedPreferences.getString("appapikey","");
        String stored_id = sharedPreferences.getString("userid","");


        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key="+stored_api_key+"&user_id="+stored_id;
        //Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        sliderList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("category");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject myCategory = jsonArray.getJSONObject(i);
                        sliderImage = myCategory.getString("cimagerl");

                        //Toast.makeText(MainActivity.this,sliderImage,Toast.LENGTH_SHORT).show();

                        sliderList.add(sliderImage);
                    }

                    final ArrayList<String> SliderArray = new ArrayList<>();

                    for(int i=0;i<sliderList.size();i++)
                        SliderArray.add(sliderList.get(i));

                    mPager = (ViewPager) findViewById(R.id.pagerSub_category);
                    mPager.setAdapter(new MyAdapter(SubCategoryActivity.this,SliderArray));
                    CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                    indicator.setViewPager(mPager);
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == sliderList.size()) {
                                currentPage = 0;
                            }
                            mPager.setCurrentItem(currentPage++, true);
                        }
                    };
                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 2500, 2500);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SubCategoryActivity.this);
        requestQueue.add(stringRequest);
    }
}
