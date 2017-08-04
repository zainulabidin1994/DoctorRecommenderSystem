package com.example.zain.doctorrecommendersystem;

import java.io.Serializable;

public class DoctorProfile implements Serializable {

    private String Name;
    private String Address;
    private String Email;
    private String HospitalName;
    private String[] Services;
    private String Number;

    public String getTheme() {
        return Theme;
    }

    public void setTheme(String theme) {
        Theme = theme;
    }

    private String Theme;

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String[] getServices() {
        return Services;
    }

    public void setServices(String[] services) {
        Services = services;
    }
}