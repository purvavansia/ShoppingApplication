package com.example.purva.shoppingapplication.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.shoppingapplication.MainActivity;
import com.example.purva.shoppingapplication.activity.ManageLoginActivity;
import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.activity.OrderConfirmActivity;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * Created by purva on 4/10/18.
 */

public class UpdateInfoFragment extends Fragment implements TextToSpeech.OnInitListener {


    ImageView pic;
    Button update;
    TextView etfname, etlname, etaddr, etemail, etphone;
    SharedPreferences sharedPreferences;
    String saved_fname, saved_lname,saved_addr, saved_email, saved_phone;
    TextToSpeech textToSpeech;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update,container,false);
        pic = view.findViewById(R.id.imageViewUploadPic);
        etfname = view.findViewById(R.id.editTextUpdateFirstName);
        etlname = view.findViewById(R.id.editTextUpdateLastName);
        etaddr = view.findViewById(R.id.editTextUpdateAddr);
        etemail = view.findViewById(R.id.editTextUpdateEmail);
        etphone = view.findViewById(R.id.editTextUpdatePhone);
        update = view.findViewById(R.id.buttonUpdateInfo);
        textToSpeech = new TextToSpeech(getActivity(), this);


        sharedPreferences = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);
        saved_fname = sharedPreferences.getString("fname","");
        saved_lname = sharedPreferences.getString("lname","");
        saved_email = sharedPreferences.getString("email","");
        saved_phone = sharedPreferences.getString("phone","");

        saved_addr = sharedPreferences.getString("address","");

        etfname.setText("");
        etlname.setText(saved_lname);
        etaddr.setText("");
        etemail.setText(saved_email);
        etphone.setText(saved_phone);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String newFname = etfname.getText().toString();
                    String newLname = etlname.getText().toString();
                    String newAddr = etaddr.getText().toString();
                    String newEmail = etemail.getText().toString();
                    String newPhone = etphone.getText().toString();


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("fname", newFname);
                    editor.putString("lname", newLname);
                    editor.putString("address", newAddr);
                    editor.putString("email", newEmail);
                    editor.putString("phone", newPhone);

                    editor.commit();
                if (isValidEmail(newEmail) && isValidName(newFname) && isValidName(newLname) && isValidPhone(newPhone)) {
                    String url = "http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?fname=" + newFname + "&lname=" + newLname + "&address=" + newAddr + "& email=" + newEmail + "&mobile=" + newPhone + "\n";

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
                    //go to activity
                    SharedPreferences sharedPreferencesforHome = getActivity().getSharedPreferences("myfile",Context.MODE_PRIVATE);
                    String appapikey = sharedPreferences.getString("appapikey","");
                    String userid = sharedPreferences.getString("userid","");
                    Intent homeIntent = new Intent(getActivity(),MainActivity.class);
                    getActivity().overridePendingTransition(R.anim.push_dowm_in, R.anim.push_down_out);
                    homeIntent.putExtra("apikey",appapikey);
                    homeIntent.putExtra("id",userid);
                    startActivity(homeIntent);
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(request);

                }
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        });




        return view;
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
           // Toast.makeText(getActivity(),"Enter a valid email address",Toast.LENGTH_SHORT).show();
            return matcher.matches();
        }
    }

    private boolean isValidName(String name){
        String EMAIL_PATTERN = "^[a-zA-Z ]+$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(name);
        if(matcher.matches()){
            return matcher.matches();
        }
        else{
            textToSpeech.speak("Please Enter a valid name ",TextToSpeech.QUEUE_FLUSH,null);
            //Toast.makeText(getActivity(),"Enter a valid name",Toast.LENGTH_SHORT).show();
            return matcher.matches();
        }

    }


    private boolean isValidPhone(String phone) {
        if (phone != null && phone.length() ==10 ) {
            return true;
        }
        else {
            textToSpeech.speak("Phone number must be atleast 10 characters ",TextToSpeech.QUEUE_FLUSH,null);
            //Toast.makeText(getActivity(),"Phone number must be atleast 10 characters",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            pic.setImageBitmap(BitmapFactory.decodeFile(picturePath));

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
