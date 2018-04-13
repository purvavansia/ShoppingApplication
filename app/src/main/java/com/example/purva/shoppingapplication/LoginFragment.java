package com.example.purva.shoppingapplication;

import android.animation.Animator;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
 * Created by purva on 4/10/18.
 *
 */

public class LoginFragment extends Fragment{

    private EditText etName, etPassword;
    private CheckBox cbRemember;
    private Button signin, create;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView forgotPassword;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        String stored_phone,store_pass;
        Boolean storedCheck;
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        etName = view.findViewById(R.id.editTextUserName);
        etPassword = view.findViewById(R.id.editTextPassword);
        cbRemember = view.findViewById(R.id.checkRemember);
        forgotPassword = view.findViewById(R.id.tvforgotPass);
        signin = view.findViewById(R.id.buttonSignin);
        create = view.findViewById(R.id.buttonCreateAcc);
        stored_phone = sharedPreferences.getString("phone","phone");
        store_pass = sharedPreferences.getString("password","1234");
         storedCheck = sharedPreferences.getBoolean("checkbox", false);
        etName.setText(stored_phone);
        etPassword.setText(store_pass);
        cbRemember.setChecked(storedCheck);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment();
                getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,forgetPasswordFragment).addToBackStack(null).commit();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String username = etName.getText().toString();
                String password = etPassword.getText().toString();
                Boolean remember = cbRemember.isChecked();
                if(cbRemember.isChecked()){
                     editor= sharedPreferences.edit();
                    editor.putString("phone",username);
                    editor.putString("password",password);
                    editor.putBoolean("checkbox", remember);


                }
                else {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password","");
                    editor.putBoolean("checkbox", false);
                    editor.commit();
                }


                String urlLogin = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?mobile="+username+"&password="+password;

                JsonArrayRequest loginRequest = new JsonArrayRequest(urlLogin, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                            try {

                                JSONObject user = (JSONObject) response.get(0);
                                String user_id = user.getString("id");
                                String apikey = user.getString("appapikey ");
                                String fname = user.getString("firstname");
                                String lname  =user.getString("lastname");
                                editor.putString("phone",username);
                                editor.putString("fname",fname);
                                editor.putString("lname",lname);
                                editor.putString("appapikey",apikey);
                                editor.putString("userid",user_id);

                                editor.commit();
                                        Intent homeIntent = new Intent(getActivity(),MainActivity.class);
                                        getActivity().overridePendingTransition(R.anim.push_dowm_in, R.anim.push_down_out);
                                        homeIntent.putExtra("apikey",apikey);
                                        homeIntent.putExtra("id",user_id);
                                        startActivity(homeIntent);



                                Log.i("Response req",apikey);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(getActivity(),""+response,Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(loginRequest);

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment signUpFragment = new SignUpFragment();
                getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,signUpFragment).addToBackStack(null).commit();
            }
        });


        return view;
    }

    /*private void getApiData() {

        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String resultUrl ="http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?mobile="+name+"&password="+password;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(resultUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i=0; i<response.length();i++) {

                        JSONObject user = (JSONObject) response.get(i);
                        String apiKey = user.getString("appapikey");
                        Toast.makeText(getActivity(),apiKey,Toast.LENGTH_SHORT).show();
                        *//*Bundle bundle = new Bundle();
                        bundle.putString("key",apiKey);*//*
                        Log.i("Response req", ""+response);

                    }

                    Intent signinIntent = new Intent(getActivity(),MainActivity.class);
                    startActivity(signinIntent);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                Toast.makeText(getActivity(),""+response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Response error", ""+error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }*/

}
