package com.example.cloudclinic_said;


import com.google.firebase.database.PropertyName;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class ListDoctorAvailable  {
    String DoctorID;
     String DoctorName;
    String Title;
    String Clinic;
    public String token;
    String itemid;

         public ListDoctorAvailable(){

            }

    public ListDoctorAvailable(String doctorID, String doctorName, String doctorSpecialist, String hospitalName, String Token, String Itemid) {

        DoctorID=doctorID;
        DoctorName = doctorName;
        Title = doctorSpecialist;
        Clinic = hospitalName;
        token=Token;
        itemid=Itemid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }


    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getClinic() {
        return Clinic;
    }

    public void setClinic(String clinic) {
        Clinic = clinic;
    }
}
