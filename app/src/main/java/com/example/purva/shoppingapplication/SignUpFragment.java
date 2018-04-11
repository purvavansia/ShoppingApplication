package com.example.purva.shoppingapplication;

import android.app.Fragment;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by purva on 4/10/18.
 */

public class SignUpFragment extends Fragment {

    private EditText etFname, etLname, etEmail,etPhone, etAddr, etPass;
    private Button btnSignUp, btnAlrdyAcc;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup,container,false);

        etFname = view.findViewById(R.id.editTextSignupName);
        etLname = view.findViewById(R.id.editTextSignupLastName);
        etEmail = view.findViewById(R.id.editTextSignupEmail);
        etPhone = view.findViewById(R.id.editTextSignupPhone);
        etAddr = view.findViewById(R.id.editTextSignupAddr);
        etPass = view.findViewById(R.id.editTextSignupPass);
        btnSignUp = view.findViewById(R.id.buttonSignup);
        btnAlrdyAcc = view.findViewById(R.id.buttonAlrdyAcc);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = etFname.getText().toString();
                String lname =etLname.getText().toString();
                String address = etAddr.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String password = etPass.getText().toString();

                if(isValidEmail(email) && isValidPassword(password) && isValidName(fname) && isValidName(lname)) {
                    String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php?fname=" + fname + "&lname=" + lname + "&address=" + address + "& email=" + email + "&mobile=" + phone + "&password=" + password + "\n";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "SignUp successful", Toast.LENGTH_SHORT).show();
                            Log.i("Response Request", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getActivity(), "Sorry SignUp failed: " + error, Toast.LENGTH_SHORT).show();
                            Log.i("Response Request Error", "" + error);
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
                }
                else{
                    Toast.makeText(getActivity(),"Please enter valid credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAlrdyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,loginFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String name){
        String EMAIL_PATTERN = "^[a-zA-Z ]+$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
