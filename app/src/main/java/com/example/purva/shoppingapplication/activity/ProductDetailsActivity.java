package com.example.purva.shoppingapplication.activity;

import android.content.Intent;
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
import com.example.purva.shoppingapplication.adapters.CustomAdapterProducts;
import com.example.purva.shoppingapplication.pojo.Products;
import com.example.purva.shoppingapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    String pid;
    ArrayList<Products> productsList;
    Products products;
    CustomAdapterProducts customAdapterProducts;
    private RecyclerView recyclerView;
    Button cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        recyclerView = findViewById(R.id.RecyclerViewProductDetails);
        cart = findViewById(R.id.buttonCartProductDetails);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailsActivity.this,CheckOutActivity.class);
                startActivity(i);
            }
        });

        //get product details and display them on recycler view

        String apiKey = getIntent().getExtras().getString("apikeyProduct");
        String userid = getIntent().getExtras().getString("user_idProduct");
        String subcid = getIntent().getExtras().getString("subcid");
        String cid = getIntent().getExtras().getString("cid");
         pid = getIntent().getExtras().getString("pid");


        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/product_details.php?cid="+cid+"&scid="+subcid+"&api_key="+apiKey+"&user_id="+userid;

        productsList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("products");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject myProducts = jsonArray.getJSONObject(i);
                        if(pid.equals(myProducts.getString("id"))) {

                            //String pid = myProducts.getString("id");
                            String pname = myProducts.getString("pname");
                            String quantity = myProducts.getString("quantity");
                            String prize = myProducts.getString("prize");
                            String discription = myProducts.getString("discription");
                            String imageurl = myProducts.getString("image");



                            products = new Products(pid, pname, quantity, prize, discription, imageurl);
                            productsList.add(products);
                        }


                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);

                    customAdapterProducts = new CustomAdapterProducts(ProductDetailsActivity.this,productsList);
                    customAdapterProducts.notifyDataSetChanged();

                    recyclerView.setAdapter(customAdapterProducts);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailsActivity.this);
        requestQueue.add(stringRequest);








    }
}
