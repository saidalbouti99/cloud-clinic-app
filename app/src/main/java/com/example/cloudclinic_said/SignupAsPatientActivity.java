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

import java.util.HashMap;
import java.util.Map;

public class SignupAsPatientActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button btn_signup_patient;
    ImageButton img_btn_back;
    EditText et_name, et_email, et_ic, et_password, et_confirm_password, et_phone;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_as_patient);
        btn_signup_patient=findViewById(R.id.btn_signup_patient_signup);
        img_btn_back=findViewById(R.id.imgBackBtn);
        et_name=findViewById(R.id.editTextTextPersonName);
        et_email=findViewById(R.id.editTextEmailAddressSignupDoctor);
        et_ic=findViewById(R.id.editTextTextIC);
        et_password=findViewById(R.id.editTextTextPasswordSignupPatient);
        et_confirm_password=findViewById(R.id.editTextTextSignupCPasswordPatient);
        et_phone=findViewById(R.id.editTextPhone);

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



        btn_signup_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=et_email.getText().toString();
                final String password=et_password.getText().toString();
                String confirmPassword=et_confirm_password.getText().toString();
                final String name=et_name.getText().toString();
                final String icno=et_ic.getText().toString();
                final String phone=et_phone.getText().toString();

                if(TextUtils.isEmpty(name)){
                    et_name.setError("Please enter your name");
                    et_name.requestFocus();
                }
                else if(TextUtils.isEmpty(icno)){
                    et_ic.setError("Please enter a valid IC number");
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
                else if (et_phone.length()>11 ) {
                    et_phone.setError("Please enter a valid phone number!");
                    et_phone.requestFocus();
                    return;
                }
                else if (et_phone.length()<10) {
                    et_phone.setError("Please enter a valid phone number!");
                    et_phone.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(email)){
                    et_email.setError("Please fill in your email");
                    et_email.requestFocus();
                }
                else if (TextUtils.isEmpty(phone)) {
                    et_phone.setError("Please fill in your phone number");
                    et_phone.requestFocus();
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
                else if(!(TextUtils.isEmpty(name) && TextUtils.isEmpty(icno) && icno.length()<14  && icno.length()>14 && et_phone.length()<10 && et_phone.length()>11
                        && TextUtils.isEmpty(email) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword)
                        && password.length()<8) && (!confirmPassword.equals(password))){

                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignupAsPatientActivity.this,"Proceed to Upload Identification Card",Toast.LENGTH_SHORT).show();

                                userID=mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=mFirestore.collection("PatientProfile").document(userID);
                                Map<String,Object> patient=new HashMap<>();
                                patient.put("Full Name",name);
                                patient.put("Email",email);
                                patient.put("ICNo",icno);
                                patient.put("PhoneNo",phone);
                                patient.put("UserType","Patient");

                                DocumentReference UserDocumentReference=mFirestore.collection("Users").document(userID);
                                Map<String,Object> user=new HashMap<>();
                                user.put("Email",email);
                                user.put("Name",name);
                                user.put("isPatient","1");

                                documentReference.set(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: Patient Profile is created for "+userID);

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
                                Intent intent2=new Intent(SignupAsPatientActivity.this,UploadICPatientActivity.class);
                                startActivity(intent2);

                            }
                            else{
                                Toast.makeText(SignupAsPatientActivity.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                else{
                    Toast.makeText(SignupAsPatientActivity.this,"Sorry,error has occured",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



}
