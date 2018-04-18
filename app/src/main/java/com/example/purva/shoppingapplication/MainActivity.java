package com.example.purva.shoppingapplication;

import android.content.Intent;
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
import com.example.purva.shoppingapplication.activity.CheckOutActivity;
import com.example.purva.shoppingapplication.activity.ManageLoginActivity;
import com.example.purva.shoppingapplication.activity.ManageNavActivity;
import com.example.purva.shoppingapplication.activity.OrderHistroyActivity;
import com.example.purva.shoppingapplication.activity.ProductDetailsActivity;
import com.example.purva.shoppingapplication.activity.WhishListActivity;
import com.example.purva.shoppingapplication.adapters.CustomAdapter;
import com.example.purva.shoppingapplication.adapters.MyAdapter;
import com.example.purva.shoppingapplication.pojo.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<Category> categories;
    Category category;
    String sliderImage;
    private static ViewPager mPager;
    private static int currentPage = 0;
    public List<String> sliderList;
    Button cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cart = findViewById(R.id.buttonCartHome);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CheckOutActivity.class);
                startActivity(i);
            }
        });
        String id = getIntent().getExtras().getString("id");
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

                        category = new Category(name,image,cdiscription,cid);
                        categories.add(category);

                    }
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
                    CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, categories);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });*/

        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean isLogin = false;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Login) {
            isLogin = true;
            Intent intentLogin = new Intent(MainActivity.this,ManageLoginActivity.class);
            intentLogin.putExtra("action",isLogin);
            startActivity(intentLogin);
            return true;
        }
        else if (id == R.id.action_SignUp) {
            isLogin = false;
            Intent intentSignup = new Intent(MainActivity.this,ManageLoginActivity.class);
            intentSignup.putExtra("action",isLogin);
            startActivity(intentSignup);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent navIntent = new Intent(MainActivity.this,ManageNavActivity.class);
            navIntent.putExtra("selection","profile");
            startActivity(navIntent);
        } else if (id == R.id.nav_orders) {
            Intent i = new Intent(MainActivity.this,OrderHistroyActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_logout) {
            Intent navIntent = new Intent(MainActivity.this,ManageLoginActivity.class);
            navIntent.putExtra("selection","logout");
            startActivity(navIntent);

        }
        else if (id == R.id.nav_about_us) {
            Intent navIntent = new Intent(MainActivity.this,ManageLoginActivity.class);
            //navIntent.putExtra("selection","logout");
            startActivity(navIntent);

        }
        else if (id == R.id.nav_wishList) {
            Intent navIntent = new Intent(MainActivity.this,WhishListActivity.class);
            startActivity(navIntent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init() throws InterruptedException {

        String id = getIntent().getExtras().getString("id");
        String apikey = getIntent().getExtras().getString("apikey");
        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key="+apikey+"&user_id="+id;
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

                    mPager = (ViewPager) findViewById(R.id.pager);
                    mPager.setAdapter(new MyAdapter(MainActivity.this,SliderArray));
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}
