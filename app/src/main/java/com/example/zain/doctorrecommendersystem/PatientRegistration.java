package com.example.zain.doctorrecommendersystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegistration extends AppCompatActivity {

    EditText pFirstname,pLastname,pUsername,pPassword,pAddress;

    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    Button button;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mFirebaseUser;
    DatabaseReference mFirebasedatabase;
    String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase= FirebaseDatabase.getInstance().getReference();
        button = (Button)findViewById(R.id.RegisterButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pFirstname = (EditText)findViewById(R.id.firstname);
                pLastname = (EditText)findViewById(R.id.lastname);
                pUsername = (EditText)findViewById(R.id.username);
                pPassword = (EditText)findViewById(R.id.password);
                pAddress = (EditText)findViewById(R.id.address);

                radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);
                int selectedId=radioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)findViewById(selectedId);
                radioSexButton.getText();

                final String Firstname_ = pFirstname.getText().toString();
                final String LastName_ = pLastname.getText().toString();
                final String Username_ = pUsername.getText().toString();
                final String Password_ = pPassword.getText().toString();
                final String Address_ = pAddress.getText().toString();

                if (Firstname_.length()==0){
                    pFirstname.requestFocus();
                    pFirstname.setError("Field Cannot Be Empty");
                }else if(!Firstname_.matches("[a-zA-Z]+")){
                    pFirstname.requestFocus();
                    pFirstname.setError("Enter Only Alphabatical Charecters");
                }else if (LastName_.length()==0){
                    pLastname.requestFocus();
                    pLastname.setError("Field Cannot Be Empty");
                }else if(!LastName_.matches("[a-zA-Z]+")) {
                    pLastname.requestFocus();
                    pLastname.setError("Enter Only Alphabatical Charecters");
                }else if(Username_.length()==0) {
                    pUsername.requestFocus();
                    pUsername.setError("Field Cannot Be Empty");
                }
                else if(Password_.length()<=7){
                    pPassword.requestFocus();
                    pPassword.setError("Password must be at least 8 characters long ");
                }else if(Address_.length()==0){
                    pAddress.requestFocus();
                    pAddress.setError("Field Cannot Be Empty");
                }else{

                    if(isNetworkAvailable()){

                        progressDialog.setMessage("Please Wait...");
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(Username_, Password_).addOnCompleteListener(PatientRegistration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    mFirebaseUser = mAuth.getCurrentUser();
                                    mUserId = mFirebaseUser.getUid();

                                    WriteNewPatient(mUserId,Firstname_,LastName_,Username_,Password_,Address_);
                                    Toast.makeText(PatientRegistration.this,"Successfully registered",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(PatientRegistration.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else{
                                    //display some message here
                                    Toast.makeText(PatientRegistration.this,"Registration Error",Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

                    }
                    else {

                        Toast.makeText(PatientRegistration.this,"Network is unavailable",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void WriteNewPatient(String userId,String _Firstname, String _Lastname, String _Username, String _Password, String _pAddress){

        Patient_Registration user= new Patient_Registration(_Firstname,_Lastname,_Username,_Password,_pAddress);
        mFirebasedatabase.child("Users").child(userId).setValue(user);

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

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,Patient_Login.class));


    }

}
