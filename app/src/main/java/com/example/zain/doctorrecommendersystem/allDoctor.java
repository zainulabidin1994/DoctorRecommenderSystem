package com.example.zain.doctorrecommendersystem;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class allDoctor extends AppCompatActivity {

    DoctorProfile docProObj = null;

    // Using to load data in LOADDataset() Function
    List<DoctorProfile> doctorProfile_List = new ArrayList<DoctorProfile>();


    customArrayList listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doctor);

        LoadDataset();

        int arraySize = doctorProfile_List.size();

        final String[] doctorName = new String[arraySize];
        final String[] hospitalName = new String[arraySize];
        final String[] doctorEmail = new String[arraySize];
        final String[] doctorLocation = new String[arraySize];
        final String[] doctorNumber = new String[arraySize];
        final String[] doctorTheme = new String[arraySize];
        int i = 0;

        for(DoctorProfile oneDoctor : doctorProfile_List){


            doctorName[i] = oneDoctor.getName();
            hospitalName[i] = oneDoctor.getHospitalName();
            doctorEmail[i] = oneDoctor.getEmail();
            doctorLocation[i] = oneDoctor.getAddress();
            doctorNumber[i] = oneDoctor.getNumber();
            doctorTheme[i] = oneDoctor.getTheme();


            i++;

        }

//        final ListAdapter listAdapter = new customArrayList(getContext(),doctorName,doctorTheme);
        listAdapter = new customArrayList(allDoctor.this,doctorName,doctorTheme);
        final ListView listView = (ListView)findViewById(R.id.allDoctorLIstview);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i = new Intent(allDoctor.this,ShowSingleDoctorProfile.class);
                        i.putExtra("Name",doctorName[position]);
                        i.putExtra("Email",doctorEmail[position]);
                        i.putExtra("Hospital",hospitalName[position]);
                        i.putExtra("Address",doctorLocation[position]);
                        i.putExtra("Number",doctorNumber[position]);
                        i.putExtra("Theme",doctorTheme[position]);
                        startActivity(i);

                    }
                }
        );

    }

    private void LoadDataset(){

        String data = "";

        InputStream is = this.getResources().openRawResource(R.raw.doctorprofiles);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if(is!=null){
            try{
                while ((data=reader.readLine()) != null){

                    docProObj = new DoctorProfile();

                    // Name,Hospital,Address,Email,Phone Number,Doctor Theme,Disease Handling list
                    String[] doctorProfile_Parts = data.split("\\*");

                    // Now Doctor Spliting Doctor Speciality
                    String[] DiseaseHandling_Parts = doctorProfile_Parts[6].split("\\,");

                    docProObj.setName(doctorProfile_Parts[0]);
                    docProObj.setHospitalName(doctorProfile_Parts[1]);
                    docProObj.setAddress(doctorProfile_Parts[2]);
                    docProObj.setEmail(doctorProfile_Parts[3]);
                    docProObj.setNumber(doctorProfile_Parts[4]);
                    docProObj.setTheme(doctorProfile_Parts[5]);
                    docProObj.setServices(DiseaseHandling_Parts);

                    doctorProfile_List.add(docProObj);
                }
                is.close();
            }catch (Exception e ){
                e.printStackTrace();
            }
        }

    }


}
