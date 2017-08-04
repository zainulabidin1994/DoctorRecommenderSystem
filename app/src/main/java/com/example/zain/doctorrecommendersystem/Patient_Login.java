package com.example.zain.doctorrecommendersystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static java.security.AccessController.getContext;

public class Patient_Login extends AppCompatActivity {

    Button loginButton;
    EditText username,password;
    String Username,Password;
    TextView regitserNow;
    ProgressDialog progressDialog;


    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__login);

        loginButton = (Button)findViewById(R.id.loginButton);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        regitserNow = (TextView)findViewById(R.id.notRegisterYet);
        progressDialog = new ProgressDialog(this);

        // getting firebase reference
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Username = username.getText().toString();
                Password = password.getText().toString();

                if(Username.length()==0){

                    username.requestFocus();
                    username.setError("Username Field Cannot Be Empty");

                }

                if(Password.length()==0){

                    password.requestFocus();
                    password.setError("Password Field Cannot Be Empty");

                }

                if(isNetworkAvailable()){

                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(Username, Password).addOnCompleteListener(Patient_Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                startActivity(new Intent(Patient_Login.this, MainActivity.class));

                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(Patient_Login.this, "Username or Password is Incorrect", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
                else {

                    Toast.makeText(Patient_Login.this,"Network is unavailable",Toast.LENGTH_SHORT).show();

                }

            }
        });

        // not register yet click lisetener
        regitserNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Patient_Login.this, PatientRegistration.class));

            }
        });

    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        boolean  isAvailable = false;
        if ( networkinfo != null && networkinfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;

    }

}
