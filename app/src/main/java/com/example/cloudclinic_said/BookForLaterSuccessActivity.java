package com.example.cloudclinic_said;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BookForLaterSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_for_later_success);

        Button btn_OK = findViewById(R.id.btn_ok);

//        String appointmentID=getIntent().getStringExtra("EXTRA_APPOINTMENT_ID");

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookForLaterSuccessActivity.this,PaymentActivity.class);
                startActivity(intent);

//                Intent intentPassAppID = new Intent(BookForLaterSuccessActivity.this, BookForNowActivity.class);
//                intentPassAppID.putExtra("EXTRA_APPOINTMENT_ID", appointmentID);
//                startActivity(intentPassAppID);
            }
        });
    }
}
