package com.mahesh.firebaserolemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private EditText etEmail,etPassword;
    private String email,password,uid;
    private FirebaseAuth auth;
    private DatabaseReference userTypeRef;
    private String[] rolesList={"Employee","Manager","Security"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        auth= FirebaseAuth.getInstance();
        userTypeRef = FirebaseDatabase.getInstance().getReference("roles");
    }

    public void onSignInTapped(View view) {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        authenticateUser();
    }

    private void authenticateUser() {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    switchToUserHome();
                    Toast.makeText(SignInActivity.this, "SignIn Success....", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignInActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void switchToUserHome(){
        userTypeRef.child(auth.getCurrentUser().getUid()).child("userType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userType = snapshot.getValue(String.class);
                if (userType.equals(rolesList[0])){
                    navigateToEmployeeActivity();
                }
                else if (userType.equals(rolesList[1])){
                    navigateToManagerActivity();
                }
                else if (userType.equals(rolesList[2])) {
                    navigateToSecurityActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignInActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSignUpTapped(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }

    public void navigateToManagerActivity(){
        startActivity(new Intent(SignInActivity.this,ManagerActivity.class));
    }

    public void navigateToEmployeeActivity(){
        startActivity(new Intent(SignInActivity.this,EmployeeActivity.class));
    }

    public void navigateToSecurityActivity(){
        startActivity(new Intent(SignInActivity.this,SecurityActivity.class));
    }



}
