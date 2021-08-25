package com.example.cloudclinic_said;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TestRecyclerView extends AppCompatActivity {
    private FirebaseFirestore mFirestore=FirebaseFirestore.getInstance();
    private CollectionReference testRef=mFirestore.collection("Test");

    private TestAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_view);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Query query=testRef.orderBy("PatientNames", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Test> options=new FirestoreRecyclerOptions.Builder<Test>()
                .setQuery(query,Test.class)
                .build();

        adapter=new TestAdapter(options);
        RecyclerView testRV=findViewById(R.id.recycler_view_test);


        testRV.setAdapter(adapter);
        testRV.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
