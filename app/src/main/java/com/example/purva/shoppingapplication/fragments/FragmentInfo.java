package com.example.purva.shoppingapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.purva.shoppingapplication.R;

/**
 * Created by purva on 4/16/18.
 */

public class FragmentInfo extends Fragment {

    Button buttonNext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_info,container,false);
        buttonNext = v.findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginFragment loginFragment1 = new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.linearLayoutLogin,loginFragment1).addToBackStack(null).commit();

            }
        });


        return v;
    }
}
