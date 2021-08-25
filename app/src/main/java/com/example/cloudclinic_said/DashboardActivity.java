package com.example.cloudclinic_said;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    EditText secretCodeBox;
    Button joinBtn, shareBtn, emailBtn;
    String usertype, uemail, uname;
    List <String> remails;
    TextView txtV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        joinBtn=findViewById(R.id.btnJoinTest);
//        shareBtn=findViewById(R.id.shareBtn);
        Intent in = new Intent(getIntent());
        usertype = in.getStringExtra("usertype");


        joinBtn=findViewById(R.id.btnJoinTest);
        emailBtn=findViewById(R.id.btnNotify);
        txtV=findViewById(R.id.ivSubtitle2);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();

        if(usertype.equals("patient")){
            txtV.setText("Let your doctor know you are here!");
        }
        else
        {
            txtV.setText("Let your patient know you are here!");
        }

        //DocumentReference docRef=mFirestore.collection("AppointmentRequest").document();

        //CollectionReference collectionReference = mFirestore.collection("AppointmentRequest");

        //Query query = collectionReference.whereEqualTo("meetingCode", "1");

        String userID=mFirebaseAuth.getCurrentUser().getUid();
        final DocumentReference documentReference=mFirestore.collection("Users").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                uemail = value.getString("Email");
                uname = value.getString("Name");
            }
        });

        remails = new ArrayList<>();

        if(usertype.equals("patient")){

            mFirestore.collection("DoctorProfile").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.size() != 0){
                        List<DocumentSnapshot> ds = value.getDocuments();

                        for(DocumentSnapshot v : ds){

                            Map<String, Object> mapInfo = v.getData();
                            if(mapInfo != null){
                                remails.add(v.getString("Email"));
                            }
                        }
                    }
                }
            });

        }
        else
        {
            mFirestore.collection("PatientProfile").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.size() != 0){
                        List<DocumentSnapshot> ds = value.getDocuments();

                        for(DocumentSnapshot v : ds){

                            Map<String, Object> mapInfo = v.getData();
                            if(mapInfo != null){
                                remails.add(v.getString("Email"));
                            }
                        }
                    }
                }
            });
        }

        URL serverURL;


        try {
            serverURL = new URL("https://meet.jit.si/cloudclinic");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom("Appointment")
                        .setFeatureFlag("invite.enabled", false)
//                .setFeatureFlag("meeting-name.enabled", false)
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(DashboardActivity.this, options);
            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                if(remails.isEmpty()){
                    remails.add("hihi123@gmail.com");
                }

                if(usertype.equals("patient")){
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your patient is currently in the meeting room.");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Appointment message: Your Patient Is Here");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, remails.toArray());
                }
                else
                {
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your doctor is currently in the meeting room.");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Appointment message: Your Doctor Is Here");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, remails.toArray());
                }

                if(emailIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(emailIntent);

                else
                    Toast.makeText(DashboardActivity.this, "Sorry, no app can handle this action and data." , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
