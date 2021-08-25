package com.example.cloudclinic_said;

import com.google.firebase.database.PropertyName;

public class Test {
    private String PatientNames;
    private String AppointmentTypes;

    public Test(){

    }

    public Test(String patientNames, String appointmentTypes) {
        PatientNames = patientNames;
        AppointmentTypes = appointmentTypes;
    }

    @PropertyName("PatientNames")
    public String getPatientNames() {
        return PatientNames;
    }

    public void setPatientNames(String patientNames) {
        PatientNames = patientNames;
    }

    @PropertyName("AppointmentTypes")
    public String getAppointmentTypes() {
        return AppointmentTypes;
    }

    public void setAppointmentTypes(String appointmentTypes) {
        AppointmentTypes = appointmentTypes;
    }
}
