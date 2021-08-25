package com.example.cloudclinic_said;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class FirestoreAdapter extends FirestorePagingAdapter<ListDoctorAvailable, FirestoreAdapter.DoctorListViewHolder> {

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser mFirebaseUser;
    String userID, email, id,doctorid,doctorname;
    StorageReference mStorageReference;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mFirestoreList;
    private Context context;

    private OnListItemClick  onListItemClick;


    public FirestoreAdapter(@NonNull FirestorePagingOptions<ListDoctorAvailable> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }



    @Override
    protected void onBindViewHolder(@NonNull DoctorListViewHolder holder, int position, @NonNull ListDoctorAvailable model) {

        holder.tv_doctor_name.setText(model.getDoctorName());
        holder.tv_doctor_specs.setText(model.getTitle());
        holder.tv_hospital.setText(model.getClinic());
        holder.tv_doctor_id.setText(model.getDoctorID());
        mStorageReference = FirebaseStorage.getInstance().getReference();

        doctorid=holder.tv_doctor_id.getText().toString();
        StorageReference profileRef=mStorageReference.child("users/"+doctorid+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.img_doctor);

            }
        });

    }

    @NonNull
    @Override
    public DoctorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_available_row, parent, false);
        return new DoctorListViewHolder(view);
    }

   public class DoctorListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_doctor_name, tv_doctor_specs,tv_hospital,tv_doctor_id;
        private ImageView img_doctor, img_call;

        public DoctorListViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_doctor_name = itemView.findViewById(R.id.tv_doctor_name_doctoravailable);
            tv_doctor_specs = itemView.findViewById(R.id.tv_doctor_specs_doctoravailable);
            tv_hospital = itemView.findViewById(R.id.tv_hospital_name_doctoravailable);
            img_doctor=itemView.findViewById(R.id.img_doctor_doctoravailable);
            img_call=itemView.findViewById(R.id.img_call);
            tv_doctor_id=itemView.findViewById(R.id.tv_doctor_id_doctoravailable);

           itemView.setOnClickListener(this);


        }

       @Override
       public void onClick(View v) {
           onListItemClick.onItemClick(getItem(getAdapterPosition()) , getAdapterPosition());
       }
   }

    public interface OnListItemClick{
        void onItemClick(DocumentSnapshot snapshot, int position);
    }
}
