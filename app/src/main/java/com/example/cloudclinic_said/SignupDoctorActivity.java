package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;


public class SignupDoctorActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button btn_signup_doctor;

    EditText et_name, et_email, et_ic, et_password, et_confirm_password, et_clinic, et_phone_nofortac;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    String userID;
    //DecimalFormat ic = new DecimalFormat("######-##-####");
    //ActivityMainBinding binding;

    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int CARD_NUMBER_TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int CARD_NUMBER_DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char CARD_NUMBER_DIVIDER = '-';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_signup_doctor)/**,(binding.getRoot())*/;
        btn_signup_doctor=findViewById(R.id.btn_signup_doctor_signup);
        et_name=findViewById(R.id.et_fullname_doctor_signup);
        et_email=findViewById(R.id.et_email_doctor_signup);
        et_phone_nofortac = findViewById(R.id.et_phone_nofortac);
        et_ic=findViewById(R.id.et_ic_doctor_signup);
        et_password=findViewById(R.id.et_password_doctor_signup);
        et_confirm_password=findViewById(R.id.et_confirmpassword_doctor_signup);
        et_clinic=findViewById(R.id.et_clinic_doctor_signup);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();

        et_ic.addTextChangedListener(new TextWatcher() {
            int keyDel;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et_ic.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = et_ic.getText().length();
                    if(len == 6) {
                        et_ic.setText(et_ic.getText() + "-");
                        et_ic.setSelection(et_ic.getText().length());
                    }
                    else if(len == 9){
                        et_ic.setText(et_ic.getText() + "-");
                        et_ic.setSelection(et_ic.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

//        et_phone_nofortac.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_signup_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=et_email.getText().toString();
                final String password=et_password.getText().toString();
                String confirmPassword=et_confirm_password.getText().toString();
                final String name=et_name.getText().toString();
                final String icno=/**ic.format*/et_ic.getText().toString();
                final String clinic=et_clinic.getText().toString();
                final String phone= et_phone_nofortac.getText().toString();

                if(TextUtils.isEmpty(name)){

                    et_name.setError("Please enter your name");
                    et_name.requestFocus();
                }

                else if(TextUtils.isEmpty(icno)){

                    et_ic.setError("Please fill in your IC number");
                    et_ic.requestFocus();
                }

                else if (icno.length()<14 ){
                    et_ic.setError("Please fill in a valid IC number");
                    et_ic.requestFocus();
                }

                else if (icno.length()>14 ) {
                    et_ic.setError("Please fill in a valid IC number");
                    et_ic.requestFocus();
                }
                else if (et_phone_nofortac.length()>11 ) {
                    et_phone_nofortac.setError("Please enter a valid phone number!");
                    et_phone_nofortac.requestFocus();
                    return;
                }

                else if (et_phone_nofortac.length()<10) {
                    et_phone_nofortac.setError("Please enter a valid phone number!");
                    et_phone_nofortac.requestFocus();
                    return;
                }

                else if(TextUtils.isEmpty(clinic)){
                    et_clinic.setError("Please state your workplace");
                    et_clinic.requestFocus();
                }

                else if (TextUtils.isEmpty(email)){
                    et_email.setError("Please fill in your email");
                    et_email.requestFocus();
                }
                else if (TextUtils.isEmpty(password)){
                    et_password.setError("Please fill in your password");
                    et_password.requestFocus();
                }
                else if (password.length()<8){
                    et_password.setError("Password must have minimum 8 characters");
                    et_password.requestFocus();

                }

                else if (TextUtils.isEmpty(confirmPassword)){
                    et_confirm_password.setError("Please confirm your password");
                    et_confirm_password.requestFocus();
                }

                else if (!confirmPassword.equals(password)){
                    et_confirm_password.setError("Password does not match");
                    et_confirm_password.requestFocus();
                }

                else if(!(TextUtils.isEmpty(name) && TextUtils.isEmpty(icno) && icno.length()<14 && icno.length()>14 && TextUtils.isEmpty(email)  && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword) && password.length()<8 && TextUtils.isEmpty(clinic))){

                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();

                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()){
//
                                Toast.makeText(SignupDoctorActivity.this,"You have successfully signed up",Toast.LENGTH_SHORT).show();

                                userID=mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=mFirestore.collection("DoctorProfile").document(userID);
                                Map<String,Object> patient=new HashMap<>();
                                patient.put("DoctorName",name);
                                patient.put("Email",email);
                                patient.put("ICNo",icno);
                                patient.put("Clinic",clinic);
                                patient.put("DoctorID",userID);
                                patient.put("PhoneNo",phone);



                                Intent intent3 = new Intent(SignupDoctorActivity.this,EnterOTPDoctorActivity.class);
                                intent3.putExtra("key",userID);
                                startActivity(intent3);

                                DocumentReference UserDocumentReference=mFirestore.collection("Users").document(userID);
                                Map<String,Object> user=new HashMap<>();
                                user.put("Email",email);
                                user.put("Name",name);
                                user.put("isDoctor","1");


                                documentReference.set(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: Doctor Profile is created for "+userID);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: "+e.toString());
                                    }
                                });
                                UserDocumentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: User is created for "+userID);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: "+e.toString());
                                    }
                                });
                                Intent intent2=new Intent(SignupDoctorActivity.this,TAC_Doctor_Activity.class);
                                intent2.putExtra("mobile", et_phone_nofortac.getText().toString());
                                startActivity(intent2);

                            }
                            else{
                                Toast.makeText(SignupDoctorActivity.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                else{
                    Toast.makeText(SignupDoctorActivity.this,"Sorry,error has occured",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }



}

