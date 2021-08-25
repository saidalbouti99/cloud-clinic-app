package com.example.cloudclinic_said;

public class PendingDoctorClass {
    private  String PatientName;
    private  String DoctorName;
    private String AppointmentID;
    private String AppointmentType;

    private String AppointmentSymptom;
    private String AppointmentDate;
    private String AppointmentTime;
//    private boolean AppointmentAccepted;
    private String isAccepted;

    public PendingDoctorClass(){

    }

    public PendingDoctorClass( String PatientName,String doctorName, String AppointmentType, String AppointmentSymptom, String AppointmentDate, String AppointmentTime, String appointmentAccepted) {
//        AppointmentID = appointmentID;
        this.PatientName = PatientName;
        DoctorName = doctorName;
        this.AppointmentType = AppointmentType;
        this.AppointmentSymptom = AppointmentSymptom;
        this.AppointmentDate = AppointmentDate;
        this.AppointmentTime = AppointmentTime;
//        AppointmentAccepted = appointmentAccepted;
        this.isAccepted=appointmentAccepted;


    }

//    public String getAppointmentID() {
//        return AppointmentID;
//    }
//
//    public void setAppointmentID(String appointmentID) {
//        AppointmentID = appointmentID;
//    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

//    public String getDoctorName() {
//        return DoctorName;
//    }
//
//    public void setDoctorName(String doctorName) {
//        DoctorName = doctorName;
//    }

    public String getAppointmentType() {
        return AppointmentType;
    }

    public void setAppointmentType(String AppointmentType) {
        this.AppointmentType = AppointmentType;
    }

    public String getAppointmentSymptom() {
        return AppointmentSymptom;
    }

    public void setAppointmentSymptom(String AppointmentSymptom) {
        this.AppointmentSymptom = AppointmentSymptom;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String AppointmentDate) {
        this.AppointmentDate = AppointmentDate;
    }

    public String getAppointmentTime() {
        return AppointmentTime;
    }

    public void setAppointmentTime(String AppointmentTime) {
        this.AppointmentTime = AppointmentTime;
    }

//    public boolean isAppointmentAccepted() {
//        return AppointmentAccepted;
//    }
//
//    public void setAppointmentAccepted(boolean appointmentAccepted) {
//        AppointmentAccepted = appointmentAccepted;
//    }
}
