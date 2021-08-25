package com.example.cloudclinic_said;



public class Card {

    public String fullName;
    public String cardNum;
    public String expiryDate;
    public String CVV;
    public String billAdd;

    public Card(String fullName, String cardNum, String expiryDate, String CVV, String billAdd) {
        this.fullName = fullName;
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
        this.CVV = CVV;
        this.billAdd = billAdd;
    }
}

