package com.example.fooddeliverysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAddapter addapter;
    private List<OrderModel> list;
    private AddToCartDatabase database;
    private List<CartModel> cartModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        database = new AddToCartDatabase(this);
        recyclerView = findViewById( R.id.cart_recyclerview);
        cartModelList = new ArrayList<>();

        Cursor data = database.getdata();
        if (data.getCount()>0){

//                   + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "0
//                + prdoctname + " TEXT NOT NULL, "1
//                + img + " TEXT NOT NULL , "2
//                +  " price TEXT NOT NULL, "3
//                + product_id + " TEXT NOT NULL, "4
//                + custommerid + " TEXT NOT NULL , "5
//                + totalitem + " TEXT NOT NULL , "6
//                + sellerid + " TEXT NOT NULL)";7
            while (data.moveToNext()){
//                 String id,productname,productprice,img,sellerid,custommerid,totalitm,productid;
                CartModel cartModel  =new CartModel(data.getString(0), data.getString(1),data.getString(3),data.getString(2),data.getString(7), data.getString(5),data.getString(6)  ,data.getString(4));
                cartModelList.add(cartModel);
            }
            addapter = new CartAddapter(this,cartModelList);

            recyclerView.setAdapter(addapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }


    }
}