package com.example.fooddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderCartItem extends AppCompatActivity {
    private ImageView imageView;
    private TextView name, price, totalitem, totalprice;
    private Button increment, decremntg, ordernow;
    private EditText address;
    private int product_price;
    private String productname, sellerid, id, img, productid;
    private OrderModel orderModel;
    private FirebaseAuth auth;
    private DatabaseReference reference,products;
    private String orderCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart_item);

        imageView = findViewById(R.id.cartorderimg_id);
        name = findViewById(R.id.cartordername_id);
        price = findViewById(R.id.cartorderprice_id);
        totalitem = findViewById(R.id.cartorder_item_id);
        increment = findViewById(R.id.cartorder_increment_id);
        decremntg = findViewById(R.id.cartorder_decrement_id);
        ordernow = findViewById(R.id.cartorder_ordernow_id);
        address = findViewById(R.id.shipping_address_id);
        totalprice = findViewById(R.id.cartorder_total_price_id);
        reference = FirebaseDatabase.getInstance().getReference("orders");
        products = FirebaseDatabase.getInstance().getReference().child("products");
        Intent intent = getIntent();
        productname = intent.getStringExtra("procut_name");
        sellerid = intent.getStringExtra("sellerid");
        String p = intent.getStringExtra("pricee");
        product_price = Integer.parseInt(p);
        id = intent.getStringExtra("id");
        img = intent.getStringExtra("img");
        productid = intent.getStringExtra("product_id");
        auth = FirebaseAuth.getInstance();
        totalprice.setText("Total price :"+product_price);

        Glide.with(this)
                .load(img)
                .into(imageView);



        getOrderCount();
        incDec();
        orderNow();
    }

    private void getOrderCount() {
        products.orderByChild("product_id").equalTo(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot data:snapshot.getChildren()) {
                        orderCount =  data.child("product_orderd_count").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void incDec() {

        //incrementing the value
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer item = Integer.parseInt(totalitem.getText().toString());
                item++;
                totalitem.setText(String.valueOf(item));
                String totalPrice[] = totalprice.getText().toString().split(":", 2);

                Integer currentprice = Integer.parseInt(totalPrice[1].trim());
                if (item > 1) {

                    currentprice += product_price;
                    totalPrice[1] = String.valueOf(currentprice);
                   totalprice.setText("");
                    totalprice.setText(totalPrice[0] + ":" + totalPrice[1]);
                }


            }
        });
//decrementing
        decremntg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int item = Integer.parseInt(totalitem.getText().toString());
                if ( item < 1) {
                    return;
                }
                item--;
                totalitem.setText(String.valueOf(item));

                if (item > 0){

                String totalPrice[] = totalprice.getText().toString().split(":", 2);
                Integer currentprice = Integer.parseInt(totalPrice[1].trim());

                currentprice -= product_price;
                totalPrice[1] = String.valueOf(currentprice);
                totalprice.setText("");
                totalprice.setText(totalPrice[0] + ":" + totalPrice[1]);
                }


            }
        });
    }

    private void orderNow() {
        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalitem.getText().toString().equals("0")) {
                    Toast.makeText(OrderCartItem.this, "please select atleat One item", Toast.LENGTH_SHORT).show();
                } else if (address.getText().toString().equals("")) {
                    Toast.makeText(OrderCartItem.this, "please prodived address ", Toast.LENGTH_SHORT).show();
                } else {
                    //order
//        product_id, status, seller_id, customer_id, location, custommer_address, price, totalitem, order_id,product_name,img
                    String key = reference.push().getKey();

                    orderModel = new OrderModel(productid,"0",sellerid,auth.getUid(),"0",address.getText().toString(),totalprice.getText().toString(),totalitem.getText().toString(),key,productname,img);
                    reference.child(key).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                if (orderCount == null){
                                    orderCount = "0";
                                }

                                int i = Integer.parseInt(orderCount);
                                i++;

                                products.child(productid).child("product_orderd_count").setValue(String.valueOf(i));

                                Toast.makeText(OrderCartItem.this, "Order Created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}