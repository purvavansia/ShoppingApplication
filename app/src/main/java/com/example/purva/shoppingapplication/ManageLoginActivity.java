package com.example.purva.shoppingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManageLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_login);



       /* SignUpFragment signUpFragment = new SignUpFragment();
        getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,signUpFragment).addToBackStack(null).commit();*/

       /*UpdateInfoFragment updateInfoFragment = new UpdateInfoFragment();
       getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,updateInfoFragment).addToBackStack(null).commit();*/

       if(getIntent().getExtras().getBoolean("action") == true){
            LoginFragment loginFragment = new LoginFragment();
        getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,loginFragment).addToBackStack(null).commit();
       }
       else if(getIntent().getExtras().getBoolean("action") == false){
           SignUpFragment signUpFragment = new SignUpFragment();
           getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,signUpFragment).addToBackStack(null).commit();
       }
    }
}
