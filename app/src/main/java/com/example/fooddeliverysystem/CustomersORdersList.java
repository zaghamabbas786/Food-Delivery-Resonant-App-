package com.example.fooddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

public class CustomersORdersList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomerOrderAdapter riderAdapter;
    private List<OrderModel> orderModelList;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_orders_list);

        recyclerView = findViewById(R.id.customer_order_list_recyclerview_id);
        reference = FirebaseDatabase.getInstance().getReference("orders");
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        if (status == null){
            id = "customer_id";
        }
        else {
            id = "seller_id";
        }

        reference.orderByChild(id).equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderModelList = new ArrayList<>();
                for (DataSnapshot data:snapshot.getChildren()) {
                    OrderModel orderModel = data.getValue(OrderModel.class);
                    orderModelList.add(orderModel);
                }
                riderAdapter = new CustomerOrderAdapter(CustomersORdersList.this, orderModelList,id);
                riderAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(riderAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(CustomersORdersList.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
