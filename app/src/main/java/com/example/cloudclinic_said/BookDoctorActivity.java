package com.example.cloudclinic_said;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cloudclinic_said.adapter.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookDoctorActivity extends AppCompatActivity {

    private UsersAdapter usersAdapter;
private List<ListDoctorAvailable>users;
private TextView textErrorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor);

        RecyclerView usersRecyclerView=findViewById(R.id.recycler_view_test);

        users=new ArrayList<>();
        usersAdapter=new UsersAdapter(users);
        usersRecyclerView.setAdapter(usersAdapter);
    }

    private void getUsers(){

    }
}