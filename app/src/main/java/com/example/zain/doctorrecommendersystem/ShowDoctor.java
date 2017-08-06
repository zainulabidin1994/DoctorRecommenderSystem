package com.example.zain.doctorrecommendersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor);


        ArrayList<DoctorProfile> doctorProfilesToShow = new ArrayList<DoctorProfile>();


        doctorProfilesToShow = (ArrayList<DoctorProfile>) getIntent().getSerializableExtra("doctorProfiles");


        int arraySize = doctorProfilesToShow.size();

        final String[] doctorName = new String[arraySize];
        final String[] doctorEmail = new String[arraySize];
        final String[] hospitalName = new String[arraySize];
        final String[] doctorLocation = new String[arraySize];
        final String[] doctorNumber = new String[arraySize];
        final String[] doctorTheme = new String[arraySize];

        int i = 0;

        for(DoctorProfile oneDoctor : doctorProfilesToShow){


            doctorName[i] = oneDoctor.getName();
            doctorEmail[i] = oneDoctor.getEmail();
            hospitalName[i] = oneDoctor.getHospitalName();
            doctorLocation[i] = oneDoctor.getAddress();
            doctorNumber[i] = oneDoctor.getNumber();
            doctorTheme[i] = oneDoctor.getTheme();

            i++;

        }

        ListAdapter listAdapter = new customArrayList(this,doctorName,doctorEmail);
        ListView listView = (ListView)findViewById(R.id.showDoctorListview);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i = new Intent(ShowDoctor.this,ShowSingleDoctorProfile.class);
                        i.putExtra("Name",doctorName[position]);
                        i.putExtra("Email",doctorEmail[position]);
                        i.putExtra("Hospital",hospitalName[position]);
                        i.putExtra("Address",doctorLocation[position]);
                        i.putExtra("Number",doctorNumber[position]);
                        i.putExtra("Theme",doctorTheme[position]);
                        startActivity(i);

//                        String food = String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(getApplicationContext(),position+"", Toast.LENGTH_LONG).show();

                    }
                }
        );

    }

//    @Override
//    public void onBackPressed() {
//
//    }

}









