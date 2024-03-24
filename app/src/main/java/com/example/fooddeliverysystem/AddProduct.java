package com.example.fooddeliverysystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    private ImageView img;
    private EditText price, name,restorant_address;
    private Button btnUpload;
    private DatabaseReference database;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1888;
    private Uri file;
    private String downloaduri;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        img = findViewById(R.id.add_image);
        price = findViewById(R.id.add_price);
        name = findViewById(R.id.add_name);
        btnUpload = findViewById(R.id.upload);
        database = FirebaseDatabase.getInstance().getReference().child("products");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth = FirebaseAuth.getInstance();
        restorant_address = findViewById(R.id.restorant_adress);



        //take image
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(Intent.ACTION_PICK);
                cameraIntent.setType("image/*");
                startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                upload();
            }
        });


    }

    private void upload() {
        if (file != null && !price.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !restorant_address.getText().toString().isEmpty()) {
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            ref.putFile(file)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String url = uri.toString();
                                            String pric = price.getText().toString();
                                            String pname = name.getText().toString();
                                            String address = restorant_address.getText().toString();
                                            String key = database.push().getKey();
                                            AddProductsModel addProductsModel = new AddProductsModel(url, pric, pname, auth.getUid(), key,address);
                                            database.child(key).setValue(addProductsModel);
                                        }
                                    });

                                    Toast.makeText(AddProduct.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    startActivity(new Intent(AddProduct.this, Seller.class));
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(AddProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploading "
                                                    + (int) progress + "%");

                                }

                            });

        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            file = uri;

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Display the selected image in your ImageView

                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}