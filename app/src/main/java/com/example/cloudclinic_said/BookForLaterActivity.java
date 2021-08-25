package com.example.cloudclinic_said;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookForLaterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText et_type, et_time,et_date,et_symptom,et_patient_name;
    EditText date_in;
    EditText time_in;
    Button btnProceed;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID,id, email;
    StorageReference mStorageReference;
    ImageButton img_book_apointment;

    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_for_later);
        et_symptom=findViewById(R.id.et_symptom_book_later);
        et_patient_name=findViewById(R.id.et_patient_name_booklater);

        date_in =findViewById(R.id.date_input);
        time_in=findViewById(R.id.time_input);

        date_in.setInputType(InputType.TYPE_NULL);
        time_in.setInputType(InputType.TYPE_NULL);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(date_in);
            }
        });


        time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(time_in);
            }
        });

        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();

        userID=mFirebaseAuth.getCurrentUser().getUid();
        id = mFirestore.collection("AppointmentRecord").document().getId();

        final DocumentReference documentReference=mFirestore.collection("PatientProfile").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                et_patient_name.setText(value.getString("Full Name"));
            }
        });

        btnProceed=findViewById(R.id.btn_proceed_book_later);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userID=mFirebaseAuth.getCurrentUser().getUid();
                email=mFirebaseAuth.getCurrentUser().getEmail();

                final String symptom = et_symptom.getText().toString();
                final String name = et_patient_name.getText().toString();

                if (TextUtils.isEmpty(symptom)){
                    et_symptom.setError("Please state your symptoms");
                    et_symptom.requestFocus();
                }
                else if (TextUtils.isEmpty(name)){
                    et_patient_name.setError("Please state your name");
                    et_patient_name.requestFocus();
                }
                else {
                DocumentReference AppointmentRef=mFirestore.collection("AppointmentRecord").document(email)
                        .collection("AppointmentID").document(id);

                final Map<String, Object> book= new HashMap<>();
                book.put("AppointmentID",id);
                book.put("AppointmentSymptom",et_symptom.getText().toString());
                book.put("AppointmentDate",date_in.getText().toString());
                book.put("AppointmentTime",time_in.getText().toString());
                book.put("PatientName",et_patient_name.getText().toString());

                AppointmentRef.set(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BookForLaterActivity.this,"Book Appointment Successful",Toast.LENGTH_SHORT).show();
                        Intent proceed = new Intent(BookForLaterActivity.this, BookForNowActivity.class);
                        startActivity(proceed);

                        Intent intentPassAppID = new Intent(BookForLaterActivity.this, BookForNowActivity.class);
                        intentPassAppID.putExtra("EXTRA_APPOINTMENT_ID", id);
                        startActivity(intentPassAppID);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookForLaterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                DocumentReference RequestRef=mFirestore.collection("AppointmentRequest").document(id);
                email=mFirebaseAuth.getCurrentUser().getEmail();

                final Map<String, Object> request= new HashMap<>();
                request.put("AppointmentID",id);
                request.put("AppointmentSymptom",et_symptom.getText().toString());
                request.put("AppointmentDate",date_in.getText().toString());
                request.put("AppointmentTime",time_in.getText().toString());
                request.put("PatientName",et_patient_name.getText().toString());
                request.put("PatientEmail",email);

                RequestRef.set(request);
            }
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2000){
            if (resultCode == Activity.RESULT_OK){
                Uri ImageUri=data.getData();
                //img_profile_patient.setImageURI(ImageUri);

                uploadImageToFirebase(ImageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri ImageUri) {

        final DocumentReference documentReference=mFirestore.collection("AppointmentRecord").document(userID).collection("AppointmentID").document(id);

        final  StorageReference fileRef=mStorageReference.child("appointment/"+mFirebaseAuth.getCurrentUser().getEmail()+"/"+id+"/appointment.jpg");


        fileRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(img_book_apointment);

                    }
                });
                Toast.makeText(BookForLaterActivity.this,"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookForLaterActivity.this,"Image Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTimeDialog(final EditText time_in) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(BookForLaterActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),false).show();
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(BookForLaterActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar
                .get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
