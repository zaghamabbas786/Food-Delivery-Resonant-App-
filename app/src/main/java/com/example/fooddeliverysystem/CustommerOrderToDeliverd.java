package com.example.fooddeliverysystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustommerOrderToDeliverd extends AppCompatActivity {
    private ImageView imageView;
    private TextView name, price, status;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LatLng latLng;
    private SupportMapFragment mapView;
    private GoogleMap googleMap;
    private Button order_pick,stop;
    private DatabaseReference reference;
    private String orderid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommer_order_to_deliverd);

        imageView = findViewById(R.id.image);
        name = findViewById(R.id.p_name);
        price = findViewById(R.id.pprice);
        status = findViewById(R.id.pstatus);
        mapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_mapView);
        order_pick = findViewById(R.id.orderstatus);
        stop =findViewById(R.id.stopsharing);
        reference = FirebaseDatabase.getInstance().getReference("orders");


        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        String procut_name = intent.getStringExtra("procut_name");
        String sellerid = intent.getStringExtra("seller_id");
        String pric = intent.getStringExtra("price");
        String product_id = intent.getStringExtra("product_id");
        String status1 = intent.getStringExtra("status");
         orderid = intent.getStringExtra("orderid");
        String location = intent.getStringExtra("location");
        Glide.with(this)
                .load(img)
                .into(imageView);
        name.setText("Product name : " + procut_name);
        price.setText("Price : " + pric);
        status.setText("Status : " + status1);

        if (status1.equals("1")) {
            order_pick.setText("Order Taken");
        }


        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location loaction : locationResult.getLocations()) {

                    latLng = new LatLng(loaction.getLatitude(), loaction.getLongitude());

                    reference.child(orderid).child("location").setValue(String.valueOf(latLng.latitude+","+latLng.longitude));

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
            }
        };


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        orderStatus(status1);
        stopsharing();
    }

    private void stopsharing() {
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CustommerOrderToDeliverd.this, "stoped", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void orderStatus( String s) {
        if (s.equals("1")){
            checksetting();
        }
        order_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderModel orderModel = new OrderModel();
                orderModel.setStatus("1");
                reference.child(orderid).child("status").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CustommerOrderToDeliverd.this, "Order Taken", Toast.LENGTH_SHORT).show();
                            order_pick.setText("Order Taken");
                            checksetting();
                        }
                    }
                });

            }
        });
    }

    public void checksetting() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(CustommerOrderToDeliverd.this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getLoaction();
            }
        });
    }

    private void getLoaction() {
        if (ContextCompat.checkSelfPermission(CustommerOrderToDeliverd.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(CustommerOrderToDeliverd.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLoaction();
            } else {
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        getLoaction();
    }
}