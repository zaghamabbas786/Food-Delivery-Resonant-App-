package com.example.fooddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class Dashboard extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerViewhorizental;
    private ProductAdapter adapter,horizentaladapter;
    private List<AddProductsModel> productModel,list;

    private Button logute, orderlist,cart;
    private FirebaseAuth auth;
    private DatabaseReference reference,horizentalref;
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        logute = findViewById(R.id.logout);
        orderlist = findViewById(R.id.view_orders);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("products");
        horizentalref = FirebaseDatabase.getInstance().getReference("products");
        recyclerViewhorizental = findViewById(R.id.product_recuclerview_horizental);

        searchView = findViewById(R.id.search);

        recyclerView = findViewById(R.id.product_recuclerview);
        cart = findViewById(R.id.cart);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productModel = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    AddProductsModel addProductsModel = data.getValue(AddProductsModel.class);
                    productModel.add(addProductsModel);
                }
                adapter = new ProductAdapter(productModel, Dashboard.this);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Dashboard.this, "database error", Toast.LENGTH_SHORT).show();

            }
        });

        reference.orderByChild("product_orderd_count").limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list = new ArrayList<>();

                    for (DataSnapshot data : snapshot.getChildren()) {
                        AddProductsModel model = data.getValue(AddProductsModel.class);
                        list.add(model);
                    }
                    horizentaladapter = new ProductAdapter(list, Dashboard.this);
                    horizentaladapter.notifyDataSetChanged();
                    recyclerViewhorizental.setAdapter(horizentaladapter);
                    recyclerViewhorizental.setLayoutManager(new LinearLayoutManager(Dashboard.this,LinearLayoutManager.HORIZONTAL,false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //logut

        logute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(Dashboard.this, LoginActivity.class));
            }
        });
        cartList();
        customerOrderList();

    }

    private void customerOrderList() {
        orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,CustomersORdersList.class));
            }
        });
    }

    private void cartList() {

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,CartListActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (searchView != null) {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText != null) {
                        serach(newText);

                    }
                    return true;
                }
            });
        }
    }

    private void serach(String s) {
        List<AddProductsModel> models = new ArrayList<>();
        if (models != null){

        for (AddProductsModel m : productModel) {

            if (m.getProductName().toLowerCase().contains(s.toLowerCase())) {
                models.add(m);
                adapter = new ProductAdapter(models, this);
                adapter.FilterSerach(models);
                recyclerView.setAdapter(adapter);

                horizentaladapter = new ProductAdapter(models,this);
                horizentaladapter.FilterSerach(models);

                recyclerViewhorizental.setAdapter(horizentaladapter);
            }


        }
        }
    }
}