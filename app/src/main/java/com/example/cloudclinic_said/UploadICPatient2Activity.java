package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class UploadICPatient2Activity extends AppCompatActivity {
    ImageView img_upload_ic;
    Button btn_proceed;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID;
    StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_icpatient2);

        img_upload_ic=findViewById(R.id.imgview_ic_upload2_patient);
        btn_proceed=findViewById(R.id.btn_proceed2_upload_ic_patient);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();

        StorageReference profileRef=mStorageReference.child("patientverification/"+mFirebaseAuth.getCurrentUser().getUid()+"/ic.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img_upload_ic);
            }
        });

        Button btn_proceed2=findViewById(R.id.btn_proceed2_upload_ic_patient);
        btn_proceed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UploadICPatient2Activity.this,"You have successfully upload your picture",Toast.LENGTH_SHORT).show();
                Intent proceed=new Intent(UploadICPatient2Activity.this,SignupPatientSuccessActivity.class);
                startActivity(proceed);
            }
        });
    }
}
