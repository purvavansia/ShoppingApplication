package com.example.purva.shoppingapplication.activity;

import com.example.purva.shoppingapplication.fragments.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.purva.shoppingapplication.R;

public class ManageLoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_login);

        //info activity
        FragmentInfo fragmentInfo = new FragmentInfo();
        getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,fragmentInfo).commit();

    }
}
