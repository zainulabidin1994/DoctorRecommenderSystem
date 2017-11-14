package com.example.zain.doctorrecommendersystem;

import io.realm.RealmObject;


public class searchHistoryJava extends RealmObject {

    String Symptoms;
    String Diseases;

    public String getDiseases() {
        return Diseases;
    }

    public void setDiseases(String diseases) {
        Diseases = diseases;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

}
