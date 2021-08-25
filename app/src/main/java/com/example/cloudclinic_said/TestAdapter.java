package com.example.cloudclinic_said;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TestAdapter extends FirestoreRecyclerAdapter<Test, TestAdapter.TestHolder>{


    public TestAdapter(@NonNull FirestoreRecyclerOptions<Test> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TestHolder holder, int position, @NonNull Test model) {

        holder.tv_name.setText(model.getPatientNames());
        holder.tv_type.setText(model.getAppointmentTypes());
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_row,parent,false);
        return new TestHolder(view);

    }

    class TestHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_type;

        public TestHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_doctor_name_upcomingpatient2);
            tv_type=itemView.findViewById(R.id.tv_type_appointment_pending_patient2);
        }
    }
}
