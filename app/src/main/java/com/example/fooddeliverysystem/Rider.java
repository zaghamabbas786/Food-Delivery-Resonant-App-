package com.example.fooddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class Rider extends AppCompatActivity {
    private Button logout;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private List<OrderModel> orderModelList;
    private RiderAdapter riderAdapter;
    private TextView ridertextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        logout = findViewById(R.id.rider_logout);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("orders");
        recyclerView = findViewById(R.id.order_recyclerview_id);


        ridertextview = findViewById(R.id.ridertext);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderModelList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    OrderModel orderModel = data.getValue(OrderModel.class);
                    orderModelList.add(orderModel);
                }
                riderAdapter = new RiderAdapter(Rider.this, orderModelList);
                recyclerView.setAdapter(riderAdapter);
                riderAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(Rider.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(Rider.this, LoginActivity.class));
            }
        });
    }


}