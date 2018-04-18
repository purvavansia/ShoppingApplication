package com.example.purva.shoppingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.purva.shoppingapplication.R;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BillingActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    EditText billingName, billingAddress, billingDeliveryAddr, billingphone, billingEmail;
    Button confirm;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        init();
// get the billing details and save them in shared preferences for further use.
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = billingName.getText().toString();
                String phone = billingphone.getText().toString();
                String email = billingEmail.getText().toString();
                String billAddr = billingAddress.getText().toString();
                String deliveryaddr = billingDeliveryAddr.getText().toString();

                if(isValidEmail(email) && isValidPhone(phone)) {
                    editor = sharedPreferences.edit();
                    editor.putString("billingname", name);
                    editor.putString("biillingemail", email);
                    editor.putString("billingaddr", billAddr);
                    editor.putString("deliveryaddr", deliveryaddr);
                    editor.putString("billingphone", phone);
                    editor.commit();


                    finish();
                }


            }
        });
    }

    private void init() {
        confirm = findViewById(R.id.buttonConfirm);
        billingEmail = findViewById(R.id.editTextBillingEmail);
        billingAddress = findViewById(R.id.editTextBillingAddr);
        billingDeliveryAddr =findViewById(R.id.editTextDeliveryAddr);
        billingphone = findViewById(R.id.editTextBillingPhone);
        textToSpeech = new TextToSpeech(BillingActivity.this, this);
        billingName = findViewById(R.id.editTextBillingDetailsName);
        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        String storedBillingName = sharedPreferences.getString("billingname","");
        String storedBillingEmail = sharedPreferences.getString("biillingemail","");
        String storedBillingAddr = sharedPreferences.getString("billingaddr","");
        String storedDeliveryAddr = sharedPreferences.getString("deliveryaddr","");
        String storedBillingPhone = sharedPreferences.getString("billingphone","");
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            return matcher.matches();
        }
        else{
            textToSpeech.speak("Please Enter a valid Email Address ",TextToSpeech.QUEUE_FLUSH,null);
            Toast.makeText(this,"Enter a valid email address",Toast.LENGTH_SHORT).show();
            return matcher.matches();
        }
    }



    private boolean isValidPhone(String phone) {
        if (phone != null && phone.length() ==10 ) {
            return true;
        }
        else {
            textToSpeech.speak("Phone number must be 10 characters ",TextToSpeech.QUEUE_FLUSH,null);
            //Toast.makeText(this,"Phone number must be atleast 10 characters",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = textToSpeech.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Toast.makeText(this,"Not supported language or no data",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
