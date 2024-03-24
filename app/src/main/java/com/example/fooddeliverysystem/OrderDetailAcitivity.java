package com.example.fooddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetailAcitivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView price, name,success;
    private Button order_recived;
    private String orderid;


    private LatLng latLng;
    private SupportMapFragment mapView;
    private GoogleMap googleMap;
    private DatabaseReference reference;
//    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_acitivity);
        imageView = findViewById(R.id.order_detail_image_id);
        price = findViewById(R.id.c_order_detail_price_id);
        name = findViewById(R.id.c_order_detailname_id);
        order_recived = findViewById(R.id.order_recived);
        success = findViewById(R.id.recived_success);
        mapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_mapView_tracker);
        reference = FirebaseDatabase.getInstance().getReference("orders");

        Intent intent = getIntent();

        String img = intent.getStringExtra("img");
        String procut_name = intent.getStringExtra("procut_name");
//
//
        orderid = intent.getStringExtra("orderid");
        String price1 = intent.getStringExtra("pricee");
//        String status = intent.getStringExtra("status");
//        String location = intent.getStringExtra("location");
//        Toast.makeText(this, orderid, Toast.LENGTH_SHORT).show();



            name.setText("product name : " + procut_name);


        price.setText("product price : "  + price1);


        Glide.with(this)
                .load(img)
                .into(imageView);
        reference.orderByChild("order_id").equalTo(orderid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        OrderModel orderModel = data.getValue(OrderModel.class);
//                       price.setText(orderModel.getPrice());
                      if ( orderModel.getStatus().equals("2")){
                          order_recived.setVisibility(View.GONE);
                          success.setVisibility(View.VISIBLE);

                      }
                      else {
                          //checking if there is location or not
                          if (!orderModel.getLocation().equals("0")){

                          String location[] = orderModel.getLocation().split(",", 2);
                          latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
                              mapView.getMapAsync(new OnMapReadyCallback() {
                                  @Override
                                  public void onMapReady(@NonNull GoogleMap map) {


                                      if (googleMap != null) {
                                          googleMap.clear();
                                      }

                                      googleMap = map;
                                      googleMap.setMinZoomPreference(15);
                                      googleMap.setIndoorEnabled(true);
                                      googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
                                      googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                  }
                              });
                          }

//

                      }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        orderRecived();

    }

    private void orderRecived() {
        order_recived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child(orderid).child("status").setValue("2").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(OrderDetailAcitivity.this, "isSuccessful", Toast.LENGTH_SHORT).show();
                            order_recived.setVisibility(View.GONE);
                            success.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }


}