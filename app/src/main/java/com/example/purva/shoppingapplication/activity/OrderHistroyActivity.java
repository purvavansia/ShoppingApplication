package com.example.purva.shoppingapplication.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.adapters.CustomAdapter;
import com.example.purva.shoppingapplication.adapters.CustomAdapterOrders;
import com.example.purva.shoppingapplication.pojo.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistroyActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String stored_phone, api_key, user_id;
    RecyclerView recyclerView;
    Orders orders;
    ArrayList<Orders> ordersArrayList;
    CustomAdapterOrders customAdapterOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_histroy);
       init();

        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/order_history.php?api_key="+api_key+"&user_id="+user_id+"&mobile="+stored_phone;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Order history");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject myOrders = jsonArray.getJSONObject(i);
                        String orderid = myOrders.getString("orderid");
                        String orderstatus = myOrders.getString("orderstatus");
                        String name1 = myOrders.getString("name");
                        String billingadd = myOrders.getString("billingadd");
                        String deliveryadd = myOrders.getString("deliveryadd");
                        String mobile = myOrders.getString("mobile");
                        String email = myOrders.getString("email");
                        String itemid = myOrders.getString("itemid");
                        String itemname = myOrders.getString("itemname");
                        String itemquantity = myOrders.getString("itemquantity");
                        String totalprice = myOrders.getString("totalprice");
                        String paidprice = myOrders.getString("paidprice");
                        String placedon = myOrders.getString("placedon");
                        //Toast.makeText(MainActivity.this,image,Toast.LENGTH_SHORT).show();
                        orders = new Orders(orderid,orderstatus,name1,billingadd,deliveryadd,mobile,email,itemid,itemname,itemquantity,totalprice,paidprice,placedon);
                        ordersArrayList.add(orders);

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(OrderHistroyActivity.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);
                    customAdapterOrders = new CustomAdapterOrders(ordersArrayList,OrderHistroyActivity.this);
                    customAdapterOrders.notifyDataSetChanged();
                    recyclerView.setAdapter(customAdapterOrders);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(OrderHistroyActivity.this);
        requestQueue.add(stringRequest);



    }

    private void init() {
        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        stored_phone = sharedPreferences.getString("phone","");
        api_key = sharedPreferences.getString("appapikey","");
        user_id = sharedPreferences.getString("userid","");
        recyclerView = findViewById(R.id.RecyclerViewOrderHistroyDetails);
        ordersArrayList = new ArrayList<>();
    }
}
