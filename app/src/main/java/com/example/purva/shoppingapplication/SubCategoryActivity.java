package com.example.purva.shoppingapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SubCategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Category> categories;
    Category category;
    String sliderImage;
    public List<String> sliderList;
    private static ViewPager mPager;
    private static int currentPage = 0;
    SharedPreferences sharedPreferences;
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

        /*String id = getIntent().getExtras().getString("id");
        String apikey = getIntent().getExtras().getString("apikey");
        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key="+apikey+"&user_id="+id;
        //Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        categories = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("category");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject myCategory = jsonArray.getJSONObject(i);
                        String image = myCategory.getString("cimagerl");
                        String name = myCategory.getString("cname");
                        String cid = myCategory.getString("cid");
                        String cdiscription = myCategory.getString("cdiscription");
                        //Toast.makeText(MainActivity.this,image,Toast.LENGTH_SHORT).show();

                        category = new Category(name,image,cid,cdiscription);
                        categories.add(category);

                    }

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



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSub_category);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
        CustomAdapter customAdapter = new CustomAdapter(SubCategoryActivity.this, categories);
        recyclerView.setAdapter(customAdapter);*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sub_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
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
