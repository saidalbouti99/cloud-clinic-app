package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPatientActivity extends AppCompatActivity {
    EditText et_email_login,et_password_login;
    FirebaseAuth mFirebaseAuth;
    TextView tv_forgot_password;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        Button btn_login_patient=findViewById(R.id.btn_login_patient);
        TextView tv_signup_patient=findViewById(R.id.tv_signup_patient);
        et_email_login=findViewById(R.id.editTextEmailAddressLoginPatient);
        et_password_login=findViewById(R.id.editTextTextPasswordLoginPatient);
        tv_forgot_password=findViewById(R.id.tv_forgot_password_patient);
        mFirebaseAuth= FirebaseAuth.getInstance();//initialize firebaseauth
        mFirestore=FirebaseFirestore.getInstance();

        btn_login_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=et_email_login.getText().toString();
                String password=et_password_login.getText().toString();

                if(TextUtils.isEmpty(email)){
                    et_email_login.setError("Please fill in your email");
                    et_email_login.requestFocus();
                }
                else if(TextUtils.isEmpty(password)){
                    et_password_login.setError("Please fill in your password");
                    et_password_login.requestFocus();
                }
                else if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(LoginPatientActivity.this,"Please fill in your email and password",Toast.LENGTH_SHORT).show();
                }
                else if(!(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))){
                                        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginPatientActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                    Toast.makeText(LoginPatientActivity.this,"Sorry,error has occured",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tv_signup_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent=new Intent(LoginPatientActivity.this,SignupAsPatientActivity.class);
                startActivity(signupIntent);
            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your registered email to receive reset link");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetEmail.getText().toString();
                        mFirebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginPatientActivity.this,"Reset link successfully sent to your email",Toast.LENGTH_SHORT).show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginPatientActivity.this,"Reset link is not sent"+ e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();


            }
        });
    }

        private void checkUserAccessLevel(String userID){
            DocumentReference documentReference=mFirestore.collection("Users").document(userID);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.getString("isPatient")!= null){
                        //if user is patient
                        Toast.makeText(LoginPatientActivity.this,"Logged in successfully" ,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),PatientHomepageActivity.class));
                        finish();
                    }
                    if (documentSnapshot.getString("isDoctor")!= null){
                        //if user is doctor
//                        Toast.makeText(LoginPatientActivity.this,"You have to login as a doctor since your account is registered as doctor" ,Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        AlertDialog.Builder loginDoctorDialog=new AlertDialog.Builder(LoginPatientActivity.this);
                        loginDoctorDialog.setTitle("Login As Doctor?");
                        loginDoctorDialog.setMessage("You have to login as doctor since your account is registered as doctor");
//                        loginDoctorDialog.setView(resetEmail);

                        loginDoctorDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                String mail=resetEmail.getText().toString();
                                startActivity(new Intent(getApplicationContext(),LoginDoctorActivity.class));

                            }
                        });
                        loginDoctorDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        loginDoctorDialog.create().show();

                    }
                }
            });
    }


}
