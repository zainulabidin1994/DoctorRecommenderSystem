package com.example.zain.doctorrecommendersystem;

import io.realm.RealmObject;


public class searchHistoryJava extends RealmObject {

    String Symptoms;

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

}
