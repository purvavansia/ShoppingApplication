package com.example.purva.shoppingapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.shoppingapplication.adapters.CustomAdapterGrid;
import com.example.purva.shoppingapplication.pojo.Products;
import com.example.purva.shoppingapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ArrayList<Products> productsList;
    Products products;
    private RecyclerView recyclerView;
    Button cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String apiKey = getIntent().getExtras().getString("apikeyProduct");
        String userid = getIntent().getExtras().getString("user_idProduct");
        String subcid = getIntent().getExtras().getString("subcid");
        String cid = getIntent().getExtras().getString("cid");
        recyclerView = findViewById(R.id.recyclerViewProducts);
        cart = findViewById(R.id.buttonCartProduct);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductActivity.this,CheckOutActivity.class);
                startActivity(i);
            }
        });

        // get products list and display them in grid layout

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
                        String pid = myProducts.getString("id");
                        String pname = myProducts.getString("pname");
                        String quantity = myProducts.getString("quantity");
                        String prize = myProducts.getString("prize");
                        String discription = myProducts.getString("discription");
                        String imageurl = myProducts.getString("image");
                        //Toast.makeText(MainActivity.this,image,Toast.LENGTH_SHORT).show();

                        products = new Products(pid, pname,quantity,prize,discription,imageurl);
                        productsList.add(products);


                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductActivity.this, 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    CustomAdapterGrid customAdapterGrid = new CustomAdapterGrid(ProductActivity.this, productsList);
                    customAdapterGrid.notifyDataSetChanged();
                    recyclerView.setAdapter(customAdapterGrid);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);
        requestQueue.add(stringRequest);





    }
}
