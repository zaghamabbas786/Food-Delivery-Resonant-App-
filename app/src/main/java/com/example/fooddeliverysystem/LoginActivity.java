package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Utilities;

public class LoginActivity extends AppCompatActivity {
    private EditText email,pasword;
    private Button login,Signup;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email =findViewById(R.id.loginemail);
        pasword =findViewById(R.id.loginpasword);
        login =findViewById(R.id.loginsignin);
        Signup =findViewById(R.id.loginsignup);
        auth=FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("'Siging in'");
        progressDialog.setMessage("please wait ...");
        database =FirebaseDatabase.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Emaile,Pasword;
                Emaile =email.getText().toString().trim();
                Pasword =pasword.getText().toString().trim();
                if (Emaile.equals("") || Pasword.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter the empty fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(Emaile,Pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Sign in successfullyu", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                database.getReference("users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String status = snapshot.child("status").getValue().toString();
                                        if (status.equals("0")){
                                            startActivity(new Intent(LoginActivity.this,Dashboard.class));
                                        }
                                        else if (status.equals("1")){
                                            startActivity(new Intent(LoginActivity.this,Rider.class));
                                        } else if (status.equals("2")){
                                            startActivity(new Intent(LoginActivity.this,Seller.class));
                                        }
                                        else if (status.equals("3")){
                                            //admin activity
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }

            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}