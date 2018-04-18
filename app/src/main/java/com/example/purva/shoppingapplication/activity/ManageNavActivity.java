package com.example.purva.shoppingapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.purva.shoppingapplication.fragments.*;

import com.example.purva.shoppingapplication.R;

public class ManageNavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_nav);

        if(getIntent().getExtras().getString("selection").equals("profile")){
            UpdateInfoFragment updateInfoFragment = new UpdateInfoFragment();
            getFragmentManager().beginTransaction().replace(R.id.linearLayoutNav,updateInfoFragment).commit();
        }

        if(getIntent().getExtras().getString("selection").equals("logout")){
            LoginFragment loginFragment = new LoginFragment();
            getFragmentManager().beginTransaction().replace(R.id.linearLayoutNav,loginFragment).commit();
        }


    }
}
