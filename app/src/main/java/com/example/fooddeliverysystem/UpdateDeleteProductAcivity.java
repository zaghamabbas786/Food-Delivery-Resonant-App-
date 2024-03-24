package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateDeleteProductAcivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText price,name,address;
    private Button update,delete;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_product_acivity);
        price = findViewById(R.id.update_price);
        imageView = findViewById(R.id.update_image);
        name = findViewById(R.id.update_name);
        address = findViewById(R.id.update_adress);
        update = findViewById(R.id.update_upload);
        delete = findViewById(R.id.delete);
        reference = FirebaseDatabase.getInstance().getReference("products");
        Intent intent = getIntent();

        String pd_name =    intent.getStringExtra("procut_name");
        String img = intent.getStringExtra("img");
        String p_rice =   intent.getStringExtra("price");
        String addres =    intent.getStringExtra("address");
        String product_id =    intent.getStringExtra("product_id");

        Glide.with(this)
                .load(img)
                .into(imageView);
        price.setText(p_rice);
        address.setText(addres);
        name.setText(pd_name);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView != null && !price.getText().toString().isEmpty() && !name.getText().toString().isEmpty()){
                    AddProductsModel model = new AddProductsModel();
                    model.setPrice(price.getText().toString());
                    model.setProductName(name.getText().toString());
                    model.setPrice(address.getText().toString());
//                    price,productName,user_id,product_id,restorant_address,product_orderd_count

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("price", price.getText().toString());
                    updates.put("productName", name.getText().toString());
                    updates.put("restorant_address", address.getText().toString());
                    reference.child(product_id).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateDeleteProductAcivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(product_id).removeValue();
                Toast.makeText(UpdateDeleteProductAcivity.this, "removed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateDeleteProductAcivity.this,Seller.class));
            }
        });

    }
}