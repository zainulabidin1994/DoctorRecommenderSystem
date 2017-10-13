package com.example.zain.doctorrecommendersystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

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
        final String[] doctorImagelink = new String[arraySize];

        int i = 0;

        for(DoctorProfile oneDoctor : doctorProfilesToShow){


            doctorName[i] = oneDoctor.getName();
            doctorEmail[i] = oneDoctor.getEmail();
            hospitalName[i] = oneDoctor.getHospitalName();
            doctorLocation[i] = oneDoctor.getAddress();
            doctorNumber[i] = oneDoctor.getNumber();
            doctorTheme[i] = oneDoctor.getTheme();
            doctorImagelink[i] = oneDoctor.getImageID();
            i++;

        }

        Integer[] imgid={
                R.drawable.doc1,
                R.drawable.doc2,
                R.drawable.doc3,
                R.drawable.doc4,
                R.drawable.doc5,
                R.drawable.doc6,
                R.drawable.doc7,
                R.drawable.doc8,
                R.drawable.doc9,
                R.drawable.doc10,
                R.drawable.doc11,
                R.drawable.doc12,
                R.drawable.doc13,
                R.drawable.doc14,
                R.drawable.doc15,
                R.drawable.doc16,
                R.drawable.doc17,
                R.drawable.doc18,
                R.drawable.doc19,
                R.drawable.doc20,
                R.drawable.doc21,
                R.drawable.doc22,
                R.drawable.doc23,
                R.drawable.doc24,
                R.drawable.doc25,
                R.drawable.doc26,
                R.drawable.doc27,
                R.drawable.doc28,
                R.drawable.doc29,
                R.drawable.doc30,
        };

        final Integer imgid2[] = new Integer[doctorImagelink.length];

        for(int j=0;j<doctorImagelink.length;j++){
            Integer hold_num = Integer.parseInt(doctorImagelink[j]);
            hold_num = hold_num-1;
            imgid2[j] = imgid[hold_num];
        }



        final ListAdapter listAdapter = new customArrayListSD(this,doctorName,hospitalName,imgid2);
        final ListView listView = (ListView)findViewById(R.id.showDoctorListview);
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
                        String hold_link = String.valueOf(imgid2[position]);
                        i.putExtra("picLink",hold_link);
                        startActivity(i);

//                        String food = String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(getApplicationContext(),position+"", Toast.LENGTH_LONG).show();

                    }
                }
        );

    }

}









