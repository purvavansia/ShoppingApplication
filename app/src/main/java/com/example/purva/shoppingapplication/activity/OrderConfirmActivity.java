package com.example.purva.shoppingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.shoppingapplication.MainActivity;
import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.adapters.CustomAdapterOrders;
import com.example.purva.shoppingapplication.pojo.Orders;
import com.example.purva.shoppingapplication.pojo.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderConfirmActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    CustomAdapterOrders customAdapterOrders;
    RecyclerView recyclerViewOrders;
    Orders orders;
    FloatingActionButton floatingActionButton;
    Button viewHistory;
    String username, user_id,storedBillingName, storedBillingEmail,storedBillingAddr,storedDeliveryAddr, storedBillingPhone, api_key;
    private static ArrayList<Orders> ordersArrayList = new ArrayList<>();
    ArrayList<Products> length = CheckOutActivity.getProductList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        floatingActionButton = findViewById(R.id.floatingActionButton);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton.animate().xBy(10).yBy(10);
                SharedPreferences sharedPreferencesforHome = getSharedPreferences("myfile",Context.MODE_PRIVATE);
                String appapikey = sharedPreferences.getString("appapikey","");
                String userid = sharedPreferences.getString("userid","");
                Intent homeIntent = new Intent(OrderConfirmActivity.this,MainActivity.class);
                overridePendingTransition(R.anim.push_dowm_in, R.anim.push_down_out);
                homeIntent.putExtra("apikey",appapikey);
                homeIntent.putExtra("id",userid);
                startActivity(homeIntent);
            }
        });



       init();
        Bundle receive = getIntent().getExtras().getBundle("product_details");

        String final_price = receive.getString("final_price");

        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderConfirmActivity.this, OrderHistroyActivity.class);
                startActivity(i);
            }
        });

        // call order confirmed api for each item in the cart
        for(int i = 0; i<length.size() ;i++) {
            String url = "http://rjtmobile.com/aamir/e-commerce/android-app/orders.php?&item_id=" + length.get(i).getPid() + "&item_names=" + length.get(i).getPname() + "&item_quantity=" + length.get(i).getQuantity() + "&final_price=" + final_price + "&&api_key=" + api_key + "&user_id=" + user_id + "&user_name=" + username + "&billingadd=" + storedBillingAddr + "&deliveryadd=" + storedDeliveryAddr + "&mobile=" + storedBillingPhone + "&email=" + storedBillingEmail;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("Order confirmed");

                        for (int i = 0; i < jsonArray.length(); i++) {

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
                            orders = new Orders(orderid, orderstatus, name1, billingadd, deliveryadd, mobile, email, itemid, itemname, itemquantity, totalprice, paidprice, placedon);
                            ordersArrayList.add(orders);

                        }

                        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(OrderConfirmActivity.this));
                        recyclerViewOrders.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewOrders.setHasFixedSize(true);
                        customAdapterOrders = new CustomAdapterOrders(ordersArrayList, OrderConfirmActivity.this);
                        customAdapterOrders.notifyDataSetChanged();
                        recyclerViewOrders.setAdapter(customAdapterOrders);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(OrderConfirmActivity.this);
            requestQueue.add(stringRequest);
        }
        CheckOutActivity.getProductList().clear();

    }

    private void init() {
        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("billingname","");
        user_id = sharedPreferences.getString("userid", "");
        storedBillingName = sharedPreferences.getString("billingname","");
        storedBillingEmail = sharedPreferences.getString("biillingemail","");
        storedBillingAddr = sharedPreferences.getString("billingaddr","");
        storedDeliveryAddr = sharedPreferences.getString("deliveryaddr","");
        storedBillingPhone = sharedPreferences.getString("billingphone","");
        api_key = sharedPreferences.getString("appapikey","");

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        viewHistory = findViewById(R.id.buttonViewOrderHistroy);

    }

    public static ArrayList<Orders> getOrderList(){
        return ordersArrayList;
    }


}
