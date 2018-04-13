package com.example.purva.shoppingapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by purva on 4/12/18.
 */

public class ForgetPasswordFragment extends Fragment {

    EditText mobile, password;
    SharedPreferences sharedPreferences;
    Button update;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password,container,false);

        mobile = view.findViewById(R.id.editTextForgotMobile);
        password = view.findViewById(R.id.editTextForgotPassword);
        update = view.findViewById(R.id.buttonForgotPass);
        sharedPreferences = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        final String store_pass = sharedPreferences.getString("password","");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mobile.getText().toString();
                String newpassword = password.getText().toString();
                String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reset_pass.php?&mobile="+phone+"&password="+store_pass+"&newpassword="+newpassword;

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Response req",""+response);
                        Toast.makeText(getActivity(),""+response,Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                LoginFragment loginFragment = new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,loginFragment).addToBackStack(null).commit();
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(request);

            }
        });

        return view;
    }
}
