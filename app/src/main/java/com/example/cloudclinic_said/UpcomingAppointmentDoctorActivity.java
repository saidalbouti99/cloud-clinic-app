package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudclinic_said.adapter.PendingAppointmentDoctorReccylerViewAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpcomingAppointmentDoctorActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID,email,patient_email;
    StorageReference mStorageReference;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mFirestoreList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointment_doctor);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();
        mFirestoreList=findViewById(R.id.recycler_view_pending_doctor);

        userID=mFirebaseAuth.getCurrentUser().getUid();

        email=mFirebaseAuth.getCurrentUser().getEmail();

        CollectionReference ref  = mFirestore.collection("AppointmentRequest");

        //Query
        Query query=ref.whereEqualTo(FieldPath.of("DoctorID"), userID);

        //RecyclerOptions

        FirestoreRecyclerOptions<AppointmentRequest> options=new FirestoreRecyclerOptions.Builder<AppointmentRequest>()
                .setQuery(query,AppointmentRequest.class)
                .build();

        adapter=new FirestoreRecyclerAdapter<AppointmentRequest, RequestViewHolder>(options) {
            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_appointment_row,parent,false);
                return new RequestViewHolder(view);

            }
            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull AppointmentRequest model) {

                holder.tv_patient_name.setText(model.getPatientName());
                holder.tv_symptoms.setText(model.getAppointmentSymptom());
                holder.tv_date.setText(model.getAppointmentDate());
                holder.tv_time.setText(model.getAppointmentTime());
                holder.tv_id.setText(model.getAppointmentID());
                holder.tv_accepted.setText(model.getIsAccepted());
                holder.tv_patient_email.setText(model.getPatientEmail());
//                String documentId = getSnapshots().getSnapshot(position).getId();

                if (holder.tv_accepted.getText().toString().equals("true")){
                    holder.btn_confirm_request.setVisibility(View.INVISIBLE);
                    holder.tv_confirmed.setText("CONFIRMED");
                    holder.btn_join.setVisibility(View.VISIBLE);

                }
                else
                {
                    holder.btn_confirm_request.setVisibility(View.VISIBLE);
                    holder.tv_confirmed.setText("PENDING");
                    holder.btn_join.setVisibility(View.INVISIBLE);
                }


            }



        };


        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private class RequestViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_patient_name, tv_appointment_type,tv_symptoms,tv_date,tv_time,tv_id,tv_confirmed, tv_accepted, tv_patient_email;
        private Button btn_confirm_request, btn_join;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_patient_name=itemView.findViewById(R.id.tv_patient_name_upcoming_dctor);
//            tv_appointment_type=itemView.findViewById(R.id.tv_type_appointment_pending);
            tv_symptoms=itemView.findViewById(R.id.tv_symptom_pending);
            tv_date=itemView.findViewById(R.id.tv_date_pending);
            tv_time=itemView.findViewById(R.id.tv_time_pending);
            tv_id=itemView.findViewById(R.id.tv_id_pendingdoctor);
            tv_confirmed=itemView.findViewById(R.id.tv_confirmed_doctorpending);
            tv_accepted=itemView.findViewById(R.id.tv_accepted_doctor_pending);
            tv_patient_email=itemView.findViewById(R.id.tv_patient_email_doctorpending);
            btn_confirm_request=itemView.findViewById(R.id.btn_confirm_request_upcomingdoctor);
            btn_join=itemView.findViewById(R.id.btn_join_doctor_upcoming);

            String idd=tv_id.getText().toString();


            DocumentReference docRef=mFirestore.collection("AppointmentRequest").document(idd);

            btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent join=new Intent(getApplicationContext(),DashboardActivity.class);
                    join.putExtra("usertype", "doctor");
                    startActivity(join);
                }
            });

            btn_confirm_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id=tv_id.getText().toString();

                    patient_email=tv_patient_email.getText().toString();
                    DocumentReference patientRef=mFirestore.collection("AppointmentRecord").document(patient_email).collection("AppointmentID").document(id);
                    Map<String, Object> edited2= new HashMap<>();
                    edited2.put("isAccepted","true");
                    edited2.put("meetingCode","1");

                    patientRef.update(edited2);

                    DocumentReference docRef=mFirestore.collection("AppointmentRequest").document(id);
                    Map<String, Object> edited= new HashMap<>();
                    edited.put("isAccepted","true");
                    edited.put("meetingCode","1");

                    btn_confirm_request.setVisibility(View.INVISIBLE);
                    tv_confirmed.setVisibility(View.VISIBLE);
                    docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpcomingAppointmentDoctorActivity.this,"Appointment Request Confirmed",Toast.LENGTH_SHORT).show();

                        }
                    });



                }
            });

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}

