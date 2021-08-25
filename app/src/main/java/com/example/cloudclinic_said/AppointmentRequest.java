package com.example.cloudclinic_said;

import com.google.firebase.firestore.DocumentReference;

public class AppointmentRequest {
    private  String PatientName;
    private  String DoctorName;
    private String AppointmentID;
    private String AppointmentType;

    private String meetingCode;
    private String DoctorID;
    private String AppointmentSymptom;
    private String AppointmentDate;
    private String AppointmentTime;
    private String PatientEmail;
    private String isAccepted;

    public AppointmentRequest()
    {

    }
    public AppointmentRequest(String appointmentID, String patientName,String doctorName, String doctorID,String MeetingCode, String appointmentType, String appointmentSymptom, String appointmentDate, String appointmentTime, String patientEmail,String appointmentAccepted) {
        AppointmentID= appointmentID;
        PatientName = patientName;
        DoctorName=doctorName;
        DoctorID=doctorID;
        AppointmentType = appointmentType;
        AppointmentSymptom = appointmentSymptom;
        AppointmentDate = appointmentDate;
        AppointmentTime = appointmentTime;
        PatientEmail=patientEmail;
        isAccepted = appointmentAccepted;
        meetingCode=MeetingCode;
    }

    public String getMeetingCode() {
        return meetingCode;
    }

    public void setMeetingCode(String meetingCode) {
        this.meetingCode = meetingCode;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getAppointmentType() {
        return AppointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        AppointmentType = appointmentType;
    }

    public String getAppointmentSymptom() {
        return AppointmentSymptom;
    }

    public void setAppointmentSymptom(String appointmentSymptom) {
        AppointmentSymptom = appointmentSymptom;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return AppointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        AppointmentTime = appointmentTime;
    }

    public String getPatientEmail() {
        return PatientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        PatientEmail = patientEmail;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setAppointmentAccepted(String appointmentAccepted) {
        isAccepted = appointmentAccepted;
    }
}
