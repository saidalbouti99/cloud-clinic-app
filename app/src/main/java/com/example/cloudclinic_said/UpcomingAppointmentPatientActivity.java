package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudclinic_said.adapter.PendingAppointmentPatientReccylerViewAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UpcomingAppointmentPatientActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID,email,id, appointmentsID;
    StorageReference mStorageReference;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mFirestoreList;
    Button btn_join;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointment_patient);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();
        mFirestoreList=findViewById(R.id.recycler_view_pending_patient);


    userID=mFirebaseAuth.getCurrentUser().getUid();

        email=mFirebaseAuth.getCurrentUser().getEmail();
       ;
        //Query
        Query query=mFirestore.collection("AppointmentRecord").document(email).collection("AppointmentID");;
        //RecyclerOptions

        FirestoreRecyclerOptions<PendingDoctorClass> options=new FirestoreRecyclerOptions.Builder<PendingDoctorClass>()
                .setQuery(query,PendingDoctorClass.class)
                .build();

        adapter=new FirestoreRecyclerAdapter<PendingDoctorClass, AppointmentViewHolder>(options) {
            @NonNull
            @Override
            public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_appointment_patient_row,parent,false);
                return new AppointmentViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position, @NonNull PendingDoctorClass model) {

                holder.tv_doctor_name.setText(model.getDoctorName());
                holder.tv_symptoms.setText(model.getAppointmentSymptom());
                holder.tv_date.setText(model.getAppointmentDate());
                holder.tv_time.setText(model.getAppointmentTime());
                holder.tv_confirm.setText(model.getIsAccepted());

                if (holder.tv_confirm.getText().toString().equals("true")){
                    holder.tv_status.setText("Confirmed");

                    btn_join.setVisibility(View.VISIBLE);
                }
                else {
                    holder.tv_status.setText("Pending");
                }


            }

        };

        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

 }

    private class AppointmentViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_doctor_name, tv_appointment_type,tv_symptoms,tv_date,tv_time, tv_status,tv_confirm;

        Button btn_cancel;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_doctor_name=itemView.findViewById(R.id.tv_doctor_name_upcomingpatient);
            tv_symptoms=itemView.findViewById(R.id.tv_symptom_pending_patient);
            tv_date=itemView.findViewById(R.id.tv_date_pending_patient);
            tv_time=itemView.findViewById(R.id.tv_time_pending_patient);
            tv_status=itemView.findViewById(R.id.tv_status_patient_pending);
            tv_confirm=itemView.findViewById(R.id.tv_confirm_patient_pending);
            btn_join=itemView.findViewById(R.id.btn_join_patient_upcoming);
//            btn_cancel=itemView.findViewById(R.id.btn_cancel_request_patient);

            btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent join=new Intent(getApplicationContext(),DashboardActivity.class);
                    join.putExtra("usertype", "patient");
                    startActivity(join);
                }
            });

//            btn_cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DocumentSnapshot snapshot;
//                    snapshot.getReference().delete();
//                }
//            });

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

//    public void onItemClick(DocumentSnapshot snapshot, int position) {
//
//        String idd= snapshot.getString("DoctorName");
//        String appID= snapshot.getString("AppointmentID");
//
//        DocumentReference colRef=mFirestore.collection("AppointmentRecord").document(email).collection("AppointmentID").document(appID);
//        colRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                 Toast.makeText(UpcomingAppointmentPatientActivity.this,"Appointment canceled successfully",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
