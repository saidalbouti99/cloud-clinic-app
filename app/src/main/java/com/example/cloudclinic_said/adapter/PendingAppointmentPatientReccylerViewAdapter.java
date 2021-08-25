package com.example.cloudclinic_said.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudclinic_said.PendingDoctorClass;
import com.example.cloudclinic_said.R;

import java.util.List;

public class PendingAppointmentPatientReccylerViewAdapter extends RecyclerView.Adapter<PendingAppointmentPatientReccylerViewAdapter.PendingAppointmentViewHolder2>{

    public List<PendingDoctorClass> pendingAppointmentList;
    private Context context;

    public PendingAppointmentPatientReccylerViewAdapter(Context context, List<PendingDoctorClass> pendingAppointmentList) {
        this.context=context;
        this.pendingAppointmentList=pendingAppointmentList;
    }

    @NonNull
    @Override
    public PendingAppointmentViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View pending_appointment_row= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_appointment_patient_row,null);
        PendingAppointmentViewHolder2 PendingAppointmentVH=new PendingAppointmentViewHolder2(pending_appointment_row);
        return PendingAppointmentVH;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAppointmentViewHolder2 holder, int position) {
        holder.tvDoctorNamee.setText(pendingAppointmentList.get(position).getPatientName());
//        holder.tvAppointmentType.setText(pendingAppointmentList.get(position).getAppointmentType());
        holder.tvAppointmentSymptoms.setText(pendingAppointmentList.get(position).getAppointmentSymptom());
        holder.tvAppointmentDate.setText(pendingAppointmentList.get(position).getAppointmentDate());
        holder.tvAppointmentTime.setText(pendingAppointmentList.get(position).getAppointmentTime());

    }

    @Override
    public int getItemCount() {
        return pendingAppointmentList.size();
    }

    public class PendingAppointmentViewHolder2 extends RecyclerView.ViewHolder {
        public TextView tvAppointmentType,tvAppointmentSymptoms,tvAppointmentDate,tvAppointmentTime,tvDoctorNamee;

        public PendingAppointmentViewHolder2(@NonNull View itemView) {
            super(itemView);
            tvDoctorNamee=itemView.findViewById(R.id.tv_doctor_name_upcomingpatient);
//            tvAppointmentType=itemView.findViewById(R.id.tv_type_appointment_pending_patient);
            tvAppointmentSymptoms=itemView.findViewById(R.id.tv_symptom_pending_patient);
            tvAppointmentDate=itemView.findViewById(R.id.tv_date_pending_patient);
            tvAppointmentTime=itemView.findViewById(R.id.tv_time_pending_patient);

        }
    }
}
