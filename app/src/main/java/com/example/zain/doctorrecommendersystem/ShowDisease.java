package com.example.zain.doctorrecommendersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.validation.Validator;

import static java.security.AccessController.getContext;

public class ShowDisease extends AppCompatActivity {


    DoctorProfile docProObj = null;
    

    // Using to load data in LOADDataset() Function
    List<DoctorProfile> doctorProfile_List = new ArrayList<DoctorProfile>();

    //  using to add doctor founded by matching doctor profiles
    Map<Integer, List<DoctorProfile>> Doctor_Founded = new TreeMap<Integer, List<DoctorProfile>>(Collections.reverseOrder());

    String dNames = new String();
    String[] diseaseFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_disease);

        Bundle diseaseData = getIntent().getExtras();

        dNames = diseaseData.getString("DiseaseNames");

        diseaseFound = dNames.split(System.getProperty("line.separator"));

        ListAdapter buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diseaseFound);
        ListView buckysListView = (ListView) findViewById(R.id.showDiseaseListview);
        buckysListView.setAdapter(buckysAdapter);

        buckysListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getApplicationContext(), food, Toast.LENGTH_LONG).show();

                        Intent i = new Intent(ShowDisease.this,GoogleSearch.class);
                        i.putExtra("DiseaseName",food);
                        startActivity(i);
                    }
                }
        );

        LoadDataset();

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

    private void FindDoctor() {



        Doctor_Founded.clear();

        //iterate through all the list
        for (DoctorProfile docProfile : doctorProfile_List){

            int holdForSorting = 0;
            String[] docServices = docProfile.getServices();

            for(String disFound : diseaseFound){

                for(int j=0;j<docServices.length;j++){

                    if(disFound.equalsIgnoreCase(docServices[j])) {

                        holdForSorting++;

                    }
                }
            }

            if(holdForSorting>0){

                if(Doctor_Founded.containsKey(holdForSorting)){

                    List<DoctorProfile> listToPutInHashmap = Doctor_Founded.get(holdForSorting);

                    listToPutInHashmap.add(docProfile);

                    Doctor_Founded.put(holdForSorting,listToPutInHashmap);

                }else{

                    List<DoctorProfile> listToPutInHashmap = new ArrayList<DoctorProfile>();
                    listToPutInHashmap.add(docProfile);
                    Doctor_Founded.put(holdForSorting,listToPutInHashmap);


                }

            }

        }

    }

    private void ShowDoctor(){


        ArrayList<DoctorProfile> doctorProfilesToShow = new ArrayList<DoctorProfile>();

        int count = 0;

        for(Map.Entry<Integer, List<DoctorProfile>> entry : Doctor_Founded.entrySet()) {

            List<DoctorProfile> value = entry.getValue();

            for(DoctorProfile oneDoctor : value){

                doctorProfilesToShow.add(oneDoctor);
                count++;

            }

            if( count > 5 ){

                break;

            }


        }

        // passing found doctors into another activity..!


        Intent i = new Intent(this,ShowDoctor.class);
        i.putExtra("doctorProfiles",doctorProfilesToShow);
        startActivity(i);

    }

    public void findDoctorButtonClicked(View view){


        FindDoctor();

        ShowDoctor();


    }


}
