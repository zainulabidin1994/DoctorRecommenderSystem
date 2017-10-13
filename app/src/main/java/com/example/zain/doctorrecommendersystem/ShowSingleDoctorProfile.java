package com.example.zain.doctorrecommendersystem;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ShowSingleDoctorProfile extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 11;
    private String Location;
    private String doctorLatitude, doctorLongitude;
    private String currentLocationLatitide;
    private String currentLoactionLongitude;

    // Doctor Profile Declaration
    String Name = null;
    String Hospital = null;
    String Email = null;
    String doctor_Address = null;
    String doctor_Theme = null;
    String Message = "";
    String phoneNo = "";
    Integer doctorPictureId;
    ImageView doctorPicture;

    TextView Doctor_Name,Hospital_Name,Doctor_Email,Doctor_Theme,Doctor_Address;
    String userEnteredLocation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_doctor_profile);

        Doctor_Name = (TextView)findViewById(R.id.Doctor_Name);
        Hospital_Name = (TextView)findViewById(R.id.Hospital_Name);
        Doctor_Email = (TextView)findViewById(R.id.Doctor_Email);
        Doctor_Theme = (TextView)findViewById(R.id.Doctor_Theme);
        Doctor_Address = (TextView)findViewById(R.id.Doctor_Address);
        doctorPicture = (ImageView)findViewById(R.id.doctorPicture);

        Bundle data = getIntent().getExtras();

        if(data==null){
            return;
        }

        Name = data.getString("Name");
        Hospital = data.getString("Hospital");
        Email = data.getString("Email");
        doctor_Address = data.getString("Address");
        doctor_Theme = data.getString("Theme");
        phoneNo = data.getString("Number");
        doctorPictureId = Integer.valueOf(data.getString("picLink"));

        Doctor_Name.setText(Name);
        Hospital_Name.setText(Hospital);
        Doctor_Email.setText(Email);
        Doctor_Theme.setText(doctor_Theme);
        Doctor_Address.setText(doctor_Address);
        doctorPicture.setImageResource(doctorPictureId);

    }

    private void openDialog_appointment(){

        LayoutInflater inflater = LayoutInflater.from(ShowSingleDoctorProfile.this);
        View subView = inflater.inflate(R.layout.dialog_layout_appointment, null);
        final EditText Name_ = (EditText)subView.findViewById(R.id.Name);
        final EditText Time_ = (EditText)subView.findViewById(R.id.Timming);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fill The Form");
        builder.setView(subView);

        builder.setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String Name,Time;
                Name = Name_.getText().toString();
                Time = Time_.getText().toString();

                Message = "Patient : "+Name+" want to make Appointment at "+Time;

                if(Name.isEmpty()){

                    Toast.makeText(ShowSingleDoctorProfile.this,"Name field can not be empty!",Toast.LENGTH_SHORT).show();

                }if(Time.isEmpty()) {

                    Toast.makeText(ShowSingleDoctorProfile.this,"Time field can not be empty!",Toast.LENGTH_SHORT).show();

                }else{
                    sendMessage();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ShowSingleDoctorProfile.this, "Appointment Not send!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    public void sendMessage(){


        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, Message, null, null);
        Toast.makeText(getApplicationContext(), "Request send Successfully!",
                Toast.LENGTH_LONG).show();

    }

    public void buttonForGoogleMap(View view){

        if(isNetworkAvailable()){

            openDialog();

        }
        else{
            Toast.makeText(this,"Network is unavailable",Toast.LENGTH_SHORT).show();
        }
    }

    private void openDialog(){

        LayoutInflater inflater = LayoutInflater.from(ShowSingleDoctorProfile.this);
        View subView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.editText);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Location:");
        builder.setView(subView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                userEnteredLocation = subEditText.getText().toString();

                if(userEnteredLocation.isEmpty()){

                    Toast.makeText(ShowSingleDoctorProfile.this, "Text field can not be empty!", Toast.LENGTH_LONG).show();

                }else{

                    // getting latitude and longitude of doctor
                    Geocoder geocoderDoc = new Geocoder(getApplication(), Locale.getDefault());
                    try {
                        List addressList1 = geocoderDoc.getFromLocationName(doctor_Address, 1);
                        if (addressList1 != null && addressList1.size() > 0) {
                            Address address = (Address) addressList1.get(0);
                            doctorLatitude = String.valueOf(address.getLatitude());
                            doctorLongitude = String.valueOf(address.getLongitude());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // getting latitude and longitude of patient
                    Geocoder geocoderPat = new Geocoder(getApplication(), Locale.getDefault());
                    try {
                        List addressList2 = geocoderPat.getFromLocationName(userEnteredLocation, 1);
                        if (addressList2 != null && addressList2.size() > 0) {
                            Address address = (Address) addressList2.get(0);
                            currentLocationLatitide = String.valueOf(address.getLatitude());
                            currentLoactionLongitude = String.valueOf(address.getLongitude());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("Doctor_Location", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Doctor_Latitude", doctorLatitude);
                    editor.putString("Doctor_Longitude", doctorLongitude);
                    editor.putString("Current_Location_Latitude", String.valueOf(currentLocationLatitide));
                    editor.putString("Current_Location_Longitude", String.valueOf(currentLoactionLongitude));
                    editor.apply();

                    Intent i = new Intent(ShowSingleDoctorProfile.this, MapsActivity.class);
                    startActivity(i);

                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ShowSingleDoctorProfile.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    public void MakeAppointmentButtonClick(View view){


        if(isNetworkAvailable()){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},PERMISSIONS_REQUEST_CODE);
                }else{

                    openDialog_appointment();
                }
            }else{

                openDialog_appointment();
            }

        }
        else{
            Toast.makeText(this,"Network is unavailable",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openDialog_appointment();
            }
        }
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













