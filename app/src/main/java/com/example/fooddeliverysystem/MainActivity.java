package com.example.fooddeliverysystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText name, username, email, shippingadress, permanentadress, password, c_pasword;
    private Button signup, login;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private Spinner spinner;
    private FirebaseUser user;
    private static final String[] paths = {"Customer", "Rider", "Seller"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        shippingadress = findViewById(R.id.shipin_adress);
        permanentadress = findViewById(R.id.permanent_address);
        password = findViewById(R.id.pasword);
        c_pasword = findViewById(R.id.cpasword);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.signin);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("'Siging Up'");
        progressDialog.setMessage("please wait ...");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString().trim();
                String Username = username.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Shipin = shippingadress.getText().toString().trim();
                String Permanent = permanentadress.getText().toString().trim();
                String Pasword = password.getText().toString().trim();
                String C_pasowrd = c_pasword.getText().toString().trim();

                if (Name.equals("") || Username.equals("") || Email.equals("") || Shipin.equals("") || Permanent.equals("") || Pasword.equals("") || C_pasowrd.equals("")) {
                    Toast.makeText(MainActivity.this, "please Enter in empty fields", Toast.LENGTH_SHORT).show();
                } else if (!Pasword.equals(C_pasowrd)) {
                    Toast.makeText(MainActivity.this, "please Enter Same pasword", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    String status = "0";
                    String spinervalue = spinner.getSelectedItem().toString();
                    if (spinervalue.equals("Customer")) {
                        status = "0";
                    } else if (spinervalue.equals("Rider")) {
                        status = "1";
                    } else if (spinervalue.equals("Seller")) {
                        status = "2";
                    }
                    UsersSignupModel usersSignupModel = new UsersSignupModel(Name, Username, Email, Shipin, Permanent, Pasword, status);
                    auth.createUserWithEmailAndPassword(Email, Pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "sign up successfully", Toast.LENGTH_SHORT).show();
                                database.getReference("users").child(auth.getUid()).setValue(usersSignupModel);
                                progressDialog.dismiss();
                                setempty();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            private void setempty() {
                name.setText("");
                username.setText("");
                email.setText("");
                shippingadress.setText("");
                permanentadress.setText("");
                password.setText("");
                c_pasword.setText("");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            database.getReference("users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String status = snapshot.child("status").getValue().toString();
                    if (status.equals("0")) {
                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                        finish();
                    } else if (status.equals("1")) {
                        startActivity(new Intent(MainActivity.this, Rider.class));
                        finish();
                    } else if (status.equals("2")) {
                        startActivity(new Intent(MainActivity.this, Seller.class));
                        finish();
                    } else if (status.equals("3")) {
                        //admin activity
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

//        database.getReference("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String Username = snapshot.child("status").getValue().toString();
//                username.setText(Username);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}