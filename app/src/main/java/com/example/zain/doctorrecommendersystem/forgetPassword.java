package com.example.zain.doctorrecommendersystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {

    Button resetPasswordButton,backToLogin;
    EditText emailAddress;
    FirebaseAuth auth = null;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        auth = FirebaseAuth.getInstance();
        resetPasswordButton = (Button)findViewById(R.id.resetPasswordButton);
        emailAddress = (EditText)findViewById(R.id.editTextResetPassword);
        progressDialog = new ProgressDialog(this);
        backToLogin = (Button)findViewById(R.id.backToLogin);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Sending Mail...");
                progressDialog.show();

                auth.sendPasswordResetEmail(emailAddress.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(forgetPassword.this, "Email Sent", Toast.LENGTH_LONG).show();
                                    backToLogin.setVisibility(View.VISIBLE);
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(forgetPassword.this, "Error", Toast.LENGTH_LONG).show();
                                    backToLogin.setVisibility(View.VISIBLE);
                                }
                            }
                        });

            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(forgetPassword.this, Patient_Login.class));

            }
        });



    }
}
