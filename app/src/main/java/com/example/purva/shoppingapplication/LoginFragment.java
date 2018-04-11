package com.example.purva.shoppingapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by purva on 4/10/18.
 */

public class LoginFragment extends Fragment{

    private EditText etName, etPassword;
    private CheckBox cbRemember;
    private Button signin, create;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);
        etName = view.findViewById(R.id.editTextUserName);
        etPassword = view.findViewById(R.id.editTextPassword);
        cbRemember = view.findViewById(R.id.checkRemember);
        signin = view.findViewById(R.id.buttonSignin);
        create = view.findViewById(R.id.buttonCreateAcc);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etName.getText().toString();
                String password = etPassword.getText().toString();

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
}
