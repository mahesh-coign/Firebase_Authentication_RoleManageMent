package com.mahesh.firebaserolemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText etName,etEmail,etPassword,etMobile;
    private String name,mobile,email,password;
    private Spinner userTypeSpinner;
    private FirebaseAuth auth;
    private DatabaseReference userRef,userTypeRef;
    private String[] rolesList={"--Select User Type--","Employee","Manager","Security"};
    private ArrayAdapter<String> roleAdapter;
    private  String selectedUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName      = findViewById(R.id.et_name_signUp);
        etMobile    = findViewById(R.id.et_mobile_signUp);
        etEmail     = findViewById(R.id.et_email_signUp);
        etPassword  = findViewById(R.id.et_password_signUp);
        userTypeSpinner = findViewById(R.id.spinner_role_signUp);
        roleAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,rolesList);
        userTypeSpinner.setAdapter(roleAdapter);

        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");
        userTypeRef = FirebaseDatabase.getInstance().getReference("roles");


        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUserType = userTypeSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onSignUpTapped(View view) {
        name        = etName.getText().toString().trim();
        email       = etEmail.getText().toString().trim();
        mobile      = etMobile.getText().toString().trim();
        password    = etPassword.getText().toString();
        createUser();
    }

    private void createUser() {
        if (selectedUserType.equals(rolesList[0])){
            Toast.makeText(SignUpActivity.this, "please select user type ...", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                storeUserDetails();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeUserDetails() {
        String uId = auth.getCurrentUser().getUid();
        User user = new User(name,email,mobile,selectedUserType);
        userRef.child(uId).setValue(user);
        userTypeRef.child(uId).child("userType").setValue(selectedUserType);
        Toast.makeText(this, "Sign Up Success...", Toast.LENGTH_SHORT).show();
        navigateToSignIn();
    }

    public void onSignInTapped(View view) {
        navigateToSignIn();

    }

    private void navigateToSignIn() {
        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
        finish();
    }


}