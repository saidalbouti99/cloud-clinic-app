package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadICPatientActivity extends AppCompatActivity {

    ImageButton btn_upload_ic;
    Button btn_proceed_upload;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID;
    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_icpatient);

        btn_upload_ic = findViewById(R.id.img_ic_patient_upload);
        btn_proceed_upload =findViewById(R.id.btn_proceed_upload_ic_patient);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();

        StorageReference profileRef=mStorageReference.child("patientverification/"+mFirebaseAuth.getCurrentUser().getUid()+"/ic.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(btn_upload_ic);


            }
        });

        userID=mFirebaseAuth.getCurrentUser().getUid();

        final DocumentReference documentReference=mFirestore.collection("PatientIC").document(userID);

        btn_upload_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        btn_proceed_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent proceed=new Intent(UploadICPatientActivity.this,UploadICPatient2Activity.class);
                startActivity(proceed);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri ImageUri=data.getData();
                //img_profile_patient.setImageURI(ImageUri);

                uploadImageToFirebase(ImageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri ImageUri) {
        final StorageReference fileRef= mStorageReference.child("patientverification/"+mFirebaseAuth.getCurrentUser().getUid()+"/ic.jpg");
        fileRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(btn_upload_ic);

                    }
                });
                Toast.makeText(UploadICPatientActivity.this,"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadICPatientActivity.this,"Image Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
