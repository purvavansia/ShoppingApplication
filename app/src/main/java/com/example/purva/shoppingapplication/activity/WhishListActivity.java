package com.example.purva.shoppingapplication.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.purva.shoppingapplication.R;
import com.example.purva.shoppingapplication.adapters.CustomAdapterCheckout;
import com.example.purva.shoppingapplication.adapters.CustomAdapterProducts;
import com.example.purva.shoppingapplication.adapters.CustomAdapterWishList;
import com.example.purva.shoppingapplication.db.MyDBHelper;
import com.example.purva.shoppingapplication.db.MyDBHelperWish;
import com.example.purva.shoppingapplication.pojo.Products;

import java.util.ArrayList;

public class WhishListActivity extends AppCompatActivity {


    Products products;
    CustomAdapterWishList customAdapterWishList;
    SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferences;
    MyDBHelperWish myDBHelperWish;
    ArrayList<Products> productsArrayListWish;
    SwipeMenuListView swipeMenuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whish_list);


        swipeMenuListView = (SwipeMenuListView) findViewById(R.id.swipeMenuWishList);

       /* myDBHelperWish = new MyDBHelperWish(this);
        sqLiteDatabase = myDBHelperWish.getWritableDatabase();
        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        String mobile = sharedPreferences.getString("phone", "");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyDBHelperWish.TABLE_NAME + " WHERE " + MyDBHelperWish.MOBILE + " = " + mobile, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                // cursor_mobile = cursor.getString(cursor.getColumnIndex(MyDBHelper.MOBILE));
                String cursor_pid = cursor.getString(cursor.getColumnIndex(MyDBHelper.PID));
                String cursor_pname = cursor.getString(cursor.getColumnIndex(MyDBHelper.PNAME));
                String cursor_pprice = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRICE));
                String cursor_pquantity = cursor.getString(cursor.getColumnIndex(MyDBHelper.QUANTITY));
                String cursor_pdesc = cursor.getString(cursor.getColumnIndex(MyDBHelper.DESCRIPTION));
                String cursor_pimage = cursor.getString(cursor.getColumnIndex(MyDBHelper.Image));
                products = new Products(cursor_pid, cursor_pname, cursor_pquantity, cursor_pprice, cursor_pdesc, cursor_pimage);
                productsArrayListWish.add(products);


            } while (cursor.moveToNext());*/

            if(CustomAdapterProducts.getWishList().size() == 0){
                Toast.makeText(this,"Your wishlist is empty", Toast.LENGTH_SHORT).show();
            }
            else {

                customAdapterWishList = new CustomAdapterWishList(WhishListActivity.this, CustomAdapterProducts.getWishList());
                customAdapterWishList.notifyDataSetChanged();

                swipeMenuListView.setAdapter(customAdapterWishList);

            }

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {



                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(186,
                        222, 217)));
                deleteItem.setWidth(200);
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };

        swipeMenuListView.setMenuCreator(creator);

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {

                    case 0:
                        CustomAdapterProducts.getWishList().remove(position);
                        swipeMenuListView.setAdapter(new CustomAdapterWishList(WhishListActivity.this,CustomAdapterProducts.getWishList()));
                        customAdapterWishList.notifyDataSetChanged();

                        break;
                }

                return false;
            }
        });

    }

    }
