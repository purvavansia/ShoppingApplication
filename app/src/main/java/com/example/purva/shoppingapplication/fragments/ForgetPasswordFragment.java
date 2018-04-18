package com.example.purva.shoppingapplication.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import com.example.purva.shoppingapplication.R;

import java.util.Locale;

/**
 * Created by purva on 4/12/18.
 */

public class ForgetPasswordFragment extends Fragment implements TextToSpeech.OnInitListener {

    EditText mobile, password;
    SharedPreferences sharedPreferences;
    Button update;
    TextToSpeech textToSpeech;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password,container,false);

        mobile = view.findViewById(R.id.editTextForgotMobile);
        password = view.findViewById(R.id.editTextForgotPassword);
        update = view.findViewById(R.id.buttonForgotPass);
        textToSpeech = new TextToSpeech(getActivity(), this);
        sharedPreferences = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        final String store_pass = sharedPreferences.getString("password","");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mobile.getText().toString();
                String newpassword = password.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password",newpassword);
                editor.commit();
                if (isValidPassword(newpassword)) {
                    String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reset_pass.php?&mobile=" + phone + "&password=" + store_pass + "&newpassword=" + newpassword;

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i("Response req", "" + response);
                            Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    LoginFragment loginFragment = new LoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin, loginFragment).addToBackStack(null).commit();
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(request);
                }

            }
        });

        return view;
    }
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        else {
            textToSpeech.speak("Password must be atleast 6 characters ",TextToSpeech.QUEUE_FLUSH,null);
           // Toast.makeText(getActivity(),"Password must be atleast 6 characters",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = textToSpeech.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Toast.makeText(getActivity(),"Not supported language or no data",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
