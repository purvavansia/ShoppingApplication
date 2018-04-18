package com.example.purva.shoppingapplication.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.activity.CheckOutActivity;
import com.example.purva.shoppingapplication.activity.FullscreenImageActivity;
import com.example.purva.shoppingapplication.activity.WhishListActivity;
import com.example.purva.shoppingapplication.db.MyDBHelper;
import com.example.purva.shoppingapplication.db.MyDBHelperWish;
import com.example.purva.shoppingapplication.pojo.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by purva on 4/13/18.
 */

public class CustomAdapterProducts extends RecyclerView.Adapter<CustomAdapterProducts.MyViewHolder> implements TextToSpeech.OnInitListener {

    // adapter for diaplaying the product details screen
    List<Products> productsList;
    Context context;
    SharedPreferences sharedPreferences;
    TextToSpeech textToSpeech;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase, liteDatabase;
    MyDBHelperWish myDBHelperWish;
    static  ArrayList<Products> wishListProducts = new ArrayList<>();
    Intent i;
    public CustomAdapterProducts(Context context, ArrayList<Products> productlist) {
        this.context = context;
        this.productsList = productlist;
    }

    public static ArrayList<Products> getWishList(){
        return wishListProducts;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_details_row_layout, parent, false);

        CustomAdapterProducts.MyViewHolder vh = new CustomAdapterProducts.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        myDBHelper = new MyDBHelper(context);
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        final Products products = productsList.get(position);
        textToSpeech = new TextToSpeech(context, this);
        holder.tvProducName.setText("Name: "+products.getPname());
        holder.tvProductQuantity.setText("Quantity: "+products.getQuantity());
        holder.tvProductPrice.setText("Price: Rs. "+products.getPrice());
        holder.tvProductDescription.setText("->"+products.getPdescription());
        Picasso.with(context).load(products.getPimage()).into(holder.productImage);
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,FullscreenImageActivity.class);
                i.putExtra("image",products.getPimage());
                context.startActivity(i);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* ContentValues contentValues = new ContentValues();
                contentValues.put(MyDBHelper.MOBILE,mobile);
                contentValues.put(MyDBHelper.PID,saved_productID);
                contentValues.put(MyDBHelper.PNAME,saved_productname);
                contentValues.put(MyDBHelper.PRICE,saved_productprice);
                contentValues.put(MyDBHelper.QUANTITY,saved_productquantity);
                contentValues.put(MyDBHelper.DESCRIPTION,saved_productdesc);
                contentValues.put(MyDBHelper.Image,saved_productimage);

                sqLiteDatabase.beginTransaction();
                sqLiteDatabase.insert(MyDBHelper.TABLE_NAME,null,contentValues);
                sqLiteDatabase.endTransaction();*/

                Toast.makeText(context,"Added to cart",Toast.LENGTH_SHORT).show();

                i = new Intent(context,CheckOutActivity.class);

                context.startActivity(i);


                  /*i.putExtra("pimage", products.getPimage());
                i.putExtra("pname",products.getPname());
                i.putExtra("pprice",products.getPrice());
                i.putExtra("pid",products.getPid());
                i.putExtra("quantity",products.getQuantity());
                i.putExtra("description",products.getPdescription());
*/
            }
        });
        holder.wishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sharedPreferences = context.getSharedPreferences("myfile", Context.MODE_PRIVATE);
                String mobile = sharedPreferences.getString("phone","");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("wishlistpid",products.getPid());
                editor.putString("wishlistpname",products.getPname());
                editor.putString("wishlistpprice",products.getPrice());
                editor.putString("wishlistpquantity",products.getQuantity());
                editor.putString("wishlistpdesc",products.getPdescription());
                editor.putString("wishlistpimage",products.getPimage());
                editor.commit();

                wishListProducts.add(productsList.get(position));
                Toast.makeText(context,"Item added to wishList",Toast.LENGTH_SHORT).show();
               /* myDBHelperWish = new MyDBHelperWish(context);
                liteDatabase = myDBHelperWish.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDBHelperWish.MOBILE,mobile);
                contentValues.put(MyDBHelperWish.PID,products.getPid());
                contentValues.put(MyDBHelperWish.PNAME,products.getPname());
                contentValues.put(MyDBHelperWish.PRICE,products.getPrice());
                contentValues.put(MyDBHelperWish.QUANTITY,products.getQuantity());
                contentValues.put(MyDBHelperWish.DESCRIPTION,products.getPdescription());
                contentValues.put(MyDBHelperWish.Image,products.getPimage());

                sqLiteDatabase.beginTransaction();
                sqLiteDatabase.insert(MyDBHelperWish.TABLE_NAME,null,contentValues);
                sqLiteDatabase.endTransaction();
                textToSpeech.speak("Item added to wish list ",TextToSpeech.QUEUE_FLUSH,null);
                *//*Intent intent = new Intent(context,WhishListActivity.class);
                context.startActivity(intent);*/


            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = textToSpeech.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Toast.makeText(context,"Not supported language or no data",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView tvProducName, tvProductPrice,tvProductQuantity, tvProductDescription;
        Button addToCart;
        EditText quantity;
        ImageView productImage,wishImage;
        public MyViewHolder(View itemView) {
            super(itemView);

            //quantity = itemView.findViewById(R.id.editTextQuantity);
            tvProducName = itemView.findViewById(R.id.textViewProductDetailsName);
            tvProductPrice = itemView.findViewById(R.id.textViewProductDetailsPrice);
            tvProductQuantity = itemView.findViewById(R.id.textViewProductDetailsQuantity);
            tvProductDescription = itemView.findViewById(R.id.textViewProductDetailsDescriptionDetails);
            addToCart = itemView.findViewById(R.id.buttonProductDetailsAddCart);
            productImage = itemView.findViewById(R.id.imageViewProductDetailsImage);
            wishImage = itemView.findViewById(R.id.imageViewHeart);
        }
    }
}
