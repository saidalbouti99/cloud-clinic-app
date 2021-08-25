package com.example.cloudclinic_said.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudclinic_said.PendingDoctorClass;
import com.example.cloudclinic_said.R;

import java.util.List;

public class PendingAppointmentDoctorReccylerViewAdapter extends RecyclerView.Adapter<PendingAppointmentDoctorReccylerViewAdapter.PendingAppointmentViewHolder>{

    public List<PendingDoctorClass> pendingAppointmentList;
    private Context context;

    public PendingAppointmentDoctorReccylerViewAdapter(Context context,List<PendingDoctorClass> pendingAppointmentList) {
        this.context=context;
        this.pendingAppointmentList=pendingAppointmentList;
    }

    @NonNull
    @Override
    public PendingAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View pending_appointment_row= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_appointment_row,null);
        PendingAppointmentViewHolder PendingAppointmentVH=new PendingAppointmentViewHolder(pending_appointment_row);
        return PendingAppointmentVH;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAppointmentViewHolder holder, int position) {
        holder.tvPatientName.setText(pendingAppointmentList.get(position).getPatientName());
        holder.tvAppointmentType.setText(pendingAppointmentList.get(position).getAppointmentType());
        holder.tvAppointmentSymptoms.setText(pendingAppointmentList.get(position).getAppointmentSymptom());
        holder.tvAppointmentDate.setText(pendingAppointmentList.get(position).getAppointmentDate());
        holder.tvAppointmentTime.setText(pendingAppointmentList.get(position).getAppointmentTime());
        

    }

    @Override
    public int getItemCount() {
        return pendingAppointmentList.size();
    }

    public class PendingAppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAppointmentType,tvPatientName,tvAppointmentSymptoms,tvAppointmentDate,tvAppointmentTime;

        public PendingAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName=itemView.findViewById(R.id.tv_patient_name_upcoming_dctor);
//            tvAppointmentType=itemView.findViewById(R.id.tv_type_appointment_pending);
            tvAppointmentSymptoms=itemView.findViewById(R.id.tv_symptom_pending);
            tvAppointmentDate=itemView.findViewById(R.id.tv_date_pending);
            tvAppointmentTime=itemView.findViewById(R.id.tv_time_pending);

        }
    }
}
