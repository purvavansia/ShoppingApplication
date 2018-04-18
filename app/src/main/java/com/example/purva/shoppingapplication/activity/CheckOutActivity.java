package com.example.purva.shoppingapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.purva.shoppingapplication.adapters.CustomAdapterCheckout;
import com.example.purva.shoppingapplication.adapters.CustomAdapterGrid;
import com.example.purva.shoppingapplication.adapters.CustomAdapterOrders;
import com.example.purva.shoppingapplication.adapters.CustomAdapterProducts;
import com.example.purva.shoppingapplication.db.MyDBHelper;
import com.example.purva.shoppingapplication.pojo.Orders;
import com.example.purva.shoppingapplication.pojo.Products;
import com.example.purva.shoppingapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {


    final int REQUEST_CODE = 1;
    final String get_token = "http://rjtmobile.com/aamir/braintree-paypal-payment/main.php?";
    final String send_payment_details = "http://rjtmobile.com/aamir/braintree-paypal-payment/mycheckout.php?";
    String token;
    long amount;
    HashMap<String, String> paramHash;
    SQLiteDatabase sqLiteDatabase;
    LinearLayout llHolder;
    TextView paypal;
    Button billingDetails, confirmOrder;
    SwipeMenuListView recyclerView;
    Products products;
    MyDBHelper myDBHelper;
    SharedPreferences sharedPreferences;
    private static ArrayList<Products> productsArrayList =new ArrayList<>();
    CustomAdapterCheckout customAdapterCheckout;
    static Map<String,ArrayList<Products>> userCartMap = new HashMap<>();
    String price;
    Products productswish;
    String mobile;
    String cursor_pid, cursor_pname, cursor_pprice,cursor_mobile, cursor_pquantity, cursor_pdesc, cursor_pimage;
    //static ArrayList<Products> wishListProducts = new ArrayList<>();
    String username, user_id,storedBillingName, storedBillingEmail,storedBillingAddr,storedDeliveryAddr, storedBillingPhone, api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        llHolder = (LinearLayout) findViewById(R.id.llHolder);
        paypal = findViewById(R.id.textViewPaypal);
        billingDetails = findViewById(R.id.buttonbillingDetails);
        recyclerView = (SwipeMenuListView) findViewById(R.id.recyclerViewCheckOut);

        myDBHelper = new MyDBHelper(this);
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        sharedPreferences = getSharedPreferences("myfile",Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString("phone","");

        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        String mobile = sharedPreferences.getString("phone","");
        String saved_productID = sharedPreferences.getString("product_id","");
        String saved_productname = sharedPreferences.getString("product_name","");
        String saved_productprice = sharedPreferences.getString("product_price","");
        String saved_productquantity = sharedPreferences.getString("product_quantity","");
        String saved_productdesc = sharedPreferences.getString("product_desc","");
        String saved_productimage = sharedPreferences.getString("product_image","");

        /*Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+MyDBHelper.TABLE_NAME + " WHERE " + MyDBHelper.MOBILE + " = " + mobile,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {
               // cursor_mobile = cursor.getString(cursor.getColumnIndex(MyDBHelper.MOBILE));
                cursor_pid = cursor.getString(cursor.getColumnIndex(MyDBHelper.PID));
                cursor_pname = cursor.getString(cursor.getColumnIndex(MyDBHelper.PNAME));
                cursor_pprice = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRICE));
                cursor_pquantity = cursor.getString(cursor.getColumnIndex(MyDBHelper.QUANTITY));
                cursor_pdesc = cursor.getString(cursor.getColumnIndex(MyDBHelper.DESCRIPTION));
                cursor_pimage = cursor.getString(cursor.getColumnIndex(MyDBHelper.Image));


            } while (cursor.moveToNext());*/
            products = new Products(saved_productID, saved_productname, saved_productquantity, saved_productprice, saved_productdesc, saved_productimage);
            productsArrayList.add(products);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("product_id","");
        editor.putString("product_name","");
        editor.putString("product_price","");
        editor.putString("product_quantity","");
        editor.putString("product_desc","");
        editor.putString("product_image","");
        editor.commit();


            customAdapterCheckout = new CustomAdapterCheckout(CheckOutActivity.this, productsArrayList);
            //customAdapterCheckout.notifyDataSetChanged();

            recyclerView.setAdapter(customAdapterCheckout);




        billingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckOutActivity.this, BillingActivity.class);
                startActivity(intent);

            }
        });

        // to create a swipe menu for edit and delete
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {



                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(186,
                        222, 217)));
                deleteItem.setWidth(200);
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };

        recyclerView.setMenuCreator(creator);

        recyclerView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {

                    case 0:
                        productsArrayList.remove(position);
                        recyclerView.setAdapter(new CustomAdapterCheckout(CheckOutActivity.this,productsArrayList));
                        customAdapterCheckout.notifyDataSetChanged();

                        break;
                }

                return false;
            }
        });

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequest().execute();
               /* sqLiteDatabase = myDBHelper.getWritableDatabase();

                String deletequery = "DELETE FROM "+ MyDBHelper.TABLE_NAME +" WHERE " + MyDBHelper.MOBILE+"=" +mobile;

                sqLiteDatabase.execSQL(deletequery);*/

            }
        });

    }

    private void callApi() {

        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("billingname","");
        user_id = sharedPreferences.getString("userid", "");
        storedBillingName = sharedPreferences.getString("billingname","");
        storedBillingEmail = sharedPreferences.getString("biillingemail","");
        storedBillingAddr = sharedPreferences.getString("billingaddr","");
        storedDeliveryAddr = sharedPreferences.getString("deliveryaddr","");
        storedBillingPhone = sharedPreferences.getString("billingphone","");
        api_key = sharedPreferences.getString("appapikey","");
        String product_id = "";
        String  product_name="";
        String product_quantity="";
        String final_price = String.valueOf(getTotal());

        for(int i=0; i<productsArrayList.size();i++){
            if(i!= productsArrayList.size()-1){
                product_id = product_id+ productsArrayList.get(i).getPid()+",";
                product_name = product_name+ productsArrayList.get(i).getPname()+",";
                product_quantity = product_quantity+ productsArrayList.get(i).getQuantity()+",";

            }
            else {
                product_id = product_id+ productsArrayList.get(i).getPid();
                product_name = product_name+ productsArrayList.get(i).getPname();
                product_quantity = product_quantity+ productsArrayList.get(i).getQuantity();
            }
        }
        Intent i = new Intent(CheckOutActivity.this, OrderConfirmActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("final_price",final_price);
        i.putExtra("product_details",bundle);
        startActivity(i);
    }

    public static ArrayList<Products> getProductList(){
        return productsArrayList;
    }



    public static long getTotal(){
        long sum = 0;

        ArrayList<Products> tempList= new ArrayList<>();
        if(productsArrayList.size()>0 ) {
            for (int i = 0; i < productsArrayList.size(); i++) {
                if("".equals(productsArrayList.get(i).getPid())){
                    tempList.add(productsArrayList.get(i));
                }
                else {
                    sum = sum + Long.parseLong(productsArrayList.get(i).getPrice());
                }
            }
            productsArrayList.removeAll(tempList);
        }

        return sum;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Thread.sleep(1000);
            TextView total;
            total = findViewById(R.id.textViewTotal);
            total.setText("Cart Total with Tax: "+(getTotal()+getTotal()*0.01));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.d("mylog", "Result: " + stringNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server

                amount = getTotal();
                paramHash = new HashMap<>();
                paramHash.put("amount", String.valueOf(amount));
                paramHash.put("nonce", stringNonce);
                sendPaymentDetails();
                callApi();
            }
                else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            }

    }
    }

    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(CheckOutActivity.this);
        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.POST, send_payment_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Successful")) {
                            Toast.makeText(CheckOutActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CheckOutActivity.this, "Transaction failed", Toast.LENGTH_LONG).show();
                            Log.d("mylog", "Final Response: " + response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }
    private class HttpRequest extends AsyncTask {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(CheckOutActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
            progress.setCancelable(false);
            progress.setMessage("We are contacting our servers for token, Please wait");
            progress.setTitle("Getting token");
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(get_token, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    Log.d("mylog", responseBody);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                           // Toast.makeText(CheckOutActivity.this, "Successfully got token", Toast.LENGTH_SHORT).show();
                            llHolder.setVisibility(View.VISIBLE);
                        }
                    });
                    token = responseBody;
                    onBraintreeSubmit();
                    //Toast.makeText(CheckOutActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();

                }

                @Override
                public void failure(Exception exception) {
                    final Exception ex = exception;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  Toast.makeText(CheckOutActivity.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progress.dismiss();
        }
    }


}
