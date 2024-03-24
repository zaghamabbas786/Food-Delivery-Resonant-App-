package com.example.fooddeliverysystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderPage extends AppCompatActivity {
    private TextView price, name;
    private ImageView imageView;
    private Button addtocart;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private AddToCartDatabase add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        price = findViewById(R.id.price_id);
        name = findViewById(R.id.name_id);

        imageView = findViewById(R.id.image_id);
        auth = FirebaseAuth.getInstance();
        addtocart = findViewById(R.id.add_to_cart);
        reference = FirebaseDatabase.getInstance().getReference("orders");
        Intent intent = getIntent();
        String product_name = intent.getStringExtra("name");
        String product_price = intent.getStringExtra("price");
        String img = intent.getStringExtra("img");
        String product_seller_id = intent.getStringExtra("id");
        String product_id = intent.getStringExtra("product_id");
        price.setText(product_price);
        name.setText(product_name);
         add = new AddToCartDatabase(OrderPage.this);
        Glide.with(this)
                .load(img)
                .into(imageView);

//        btn_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //    product_id,status,seller_id,customer_id,location, custommer_address ,price,totalitem;
//                String key = reference.push().getKey();
//                OrderModel orderModel = new OrderModel(product_id, "0", product_seller_id, auth.getUid().toString(), "0", "dummy", "87", "2", key,product_name,img);
//                reference.child(key).setValue(orderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(OrderPage.this, "order created", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });



//        String prdoctname, String img, String price, String custommerid, String sellerid, String product_id
        addtocart(product_name,img,product_price,auth.getUid().toString(),product_seller_id,product_id);

        Cursor data = add.getdata();
        if (data.getCount()>0){
            StringBuffer stringBuffer = new StringBuffer();

            while (data.moveToNext()){
                stringBuffer.append("id" + data.getString(1) + "\n");
                stringBuffer.append("123" + data.getString(2));

            }
        }


    }

    private void addtocart(String prdoctname, String img, String price, String custommerid, String sellerid, String product_id) {

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               boolean result = add.insert(prdoctname,img,price,custommerid,sellerid,product_id,"0");
               if (result){
                   Toast.makeText(OrderPage.this, "inserted", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(OrderPage.this, "unsuccesfull", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }
}