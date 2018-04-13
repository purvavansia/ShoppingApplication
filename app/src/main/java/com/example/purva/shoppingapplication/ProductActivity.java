package com.example.purva.shoppingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class ProductActivity extends AppCompatActivity {

    ArrayList<Products> productsList;
    Products products;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String apiKey = getIntent().getExtras().getString("apikeyProduct");
        String userid = getIntent().getExtras().getString("user_idProduct");
        String subcid = getIntent().getExtras().getString("subcid");
        recyclerView = findViewById(R.id.recyclerViewProducts);

        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?id="+subcid+"&api_key="+apiKey+"&user_id="+userid;
        //Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        productsList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("product");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject myProducts = jsonArray.getJSONObject(i);
                        String pid = myProducts.getString("pid");
                        String pname = myProducts.getString("pname");
                        String quantity = myProducts.getString("quantity");
                        String prize = myProducts.getString("prize");
                        String discription = myProducts.getString("discription");
                        String imageurl = myProducts.getString("imageurl");
                        //Toast.makeText(MainActivity.this,image,Toast.LENGTH_SHORT).show();

                        products = new Products(pid, pname,quantity,prize,discription,imageurl);
                        productsList.add(products);

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
        RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);
        requestQueue.add(stringRequest);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        CustomAdapterGrid customAdapterGrid = new CustomAdapterGrid(ProductActivity.this,productsList);
        recyclerView.setAdapter(customAdapterGrid);



    }
}
