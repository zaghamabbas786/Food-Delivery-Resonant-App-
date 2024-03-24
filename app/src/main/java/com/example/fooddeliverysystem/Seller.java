package com.example.fooddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Seller extends AppCompatActivity {
    private Button logout, add_product,orders;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private AddProductAddapter productAddapter;
    private List<AddProductsModel> addProductsModels;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        logout = findViewById(R.id.seller_logout);
        add_product = findViewById(R.id.add_product);
        orders = findViewById(R.id.orderslists);

        recyclerView = findViewById(R.id.add_product_recyclerview);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        recyclerView.setLayoutManager(new LinearLayoutManager(Seller.this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addProductsModels = new ArrayList<>();
                AddProductAddapter addProductAddapter = new AddProductAddapter(Seller.this, addProductsModels);
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    AddProductsModel model = datasnapshot.getValue(AddProductsModel.class);
                    addProductsModels.add(model);

                }
                addProductAddapter.notifyDataSetChanged();

                recyclerView.setAdapter(addProductAddapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Seller.this, AddProduct.class));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(Seller.this, LoginActivity.class));
            }
        });

        ordersList();
    }
    private void ordersList() {
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Seller.this,CustomersORdersList.class);
                intent.putExtra("status","1");
                startActivity(intent);
            }
        });
    }

}