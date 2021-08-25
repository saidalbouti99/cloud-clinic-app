package com.example.cloudclinic_said;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudclinic_said.adapter.DoctorAvailableRecyclerViewAdapter;
import com.example.cloudclinic_said.listeners.UsersListener;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookForNowActivity extends AppCompatActivity implements FirestoreAdapter.OnListItemClick {
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID, email, id,doctorname;
    StorageReference mStorageReference;
    private FirestoreAdapter adapter;
    private RecyclerView mFirestoreList;
    String appointmentID;




//    private OnItemClickListener listener;
    private UsersListener usersListener;


    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_for_now);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirestoreList = findViewById(R.id.recycler_view_list_doctor);

        Intent intentGetData = getIntent();
        appointmentID=intentGetData.getStringExtra("EXTRA_APPOINTMENT_ID");



        userID = mFirebaseAuth.getCurrentUser().getUid();

        email = mFirebaseAuth.getCurrentUser().getEmail();


        id = mFirestore.collection("AppointmentRecord").document().getId();

        String appointmentID = getIntent().getStringExtra("EXTRA_APPOINTMENT_ID");



//Query
        Query query = mFirestore.collection("DoctorProfile");

        PagedList.Config config=new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();
        //RecyclerOptions
        FirestorePagingOptions<ListDoctorAvailable> options = new FirestorePagingOptions.Builder<ListDoctorAvailable>()
                .setQuery(query, config,new SnapshotParser<ListDoctorAvailable>() {
                    @NonNull
                    @Override
                    public ListDoctorAvailable parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        ListDoctorAvailable listDoctorAvailable= snapshot.toObject(ListDoctorAvailable.class);

                        return listDoctorAvailable;
                    }
                })
                .build();


        adapter=new FirestoreAdapter(options, this);

        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

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

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("ITEM_CLICK", "Clicked the item"+position+" "+snapshot.getString("DoctorName"));

        String idd= snapshot.getString("DoctorName");
        String doctorID= snapshot.getString("DoctorID");

        DocumentReference colRef=mFirestore.collection("AppointmentRecord").document(email)
                .collection("AppointmentID").document(appointmentID);

        Map<String, Object> record= new HashMap<>();
        record.put("DoctorName",idd);
        record.put("DoctorID",doctorID);
        colRef.update(record).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookForNowActivity.this,"Doctor selected successfully",Toast.LENGTH_SHORT).show();
                Intent success=new Intent(BookForNowActivity.this,BookForLaterSuccessActivity.class);
                startActivity(success);
            }
        });

        email=mFirebaseAuth.getCurrentUser().getEmail();

        DocumentReference RequestRef=mFirestore.collection("AppointmentRequest").document(appointmentID);
        Map<String, Object> request= new HashMap<>();


        request.put("DoctorName",idd);
        request.put("DoctorID",doctorID);
        RequestRef.update(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookForNowActivity.this,"Doctor selected successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }
}