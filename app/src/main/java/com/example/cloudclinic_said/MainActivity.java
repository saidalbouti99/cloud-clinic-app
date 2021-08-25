package com.example.cloudclinic_said;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn_loginasdoctor;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID;
    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_loginasdoctor=findViewById(R.id.btn_login_as_doctor);
        Button btn_loginaspatient=findViewById(R.id.btn_login_as_patient);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();


        btn_loginasdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginDoctorActivity.class);
                startActivity(intent);
            }
        });

        btn_loginaspatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,LoginPatientActivity.class);
                startActivity(intent2);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                checkUserAccessLevel(FirebaseAuth.getInstance().getCurrentUser().getUid());

//                startActivity(new Intent(getApplicationContext(), PatientHomepageActivity.class));
            }
        }
    }

    private void checkUserAccessLevel(String userID){
        DocumentReference documentReference=mFirestore.collection("Users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.getString("isPatient")!= null){
                    //if user is patient
//                    Toast.makeText(MainActivity.this,"Logged in successfully" ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),PatientHomepageActivity.class));
                    finish();
                }
                if (documentSnapshot.getString("isDoctor")!= null){
                    //if user is doctor
                    startActivity(new Intent(getApplicationContext(),DoctorHomepageActivity.class));
                    finish();

                }
            }
        });
    }
}
