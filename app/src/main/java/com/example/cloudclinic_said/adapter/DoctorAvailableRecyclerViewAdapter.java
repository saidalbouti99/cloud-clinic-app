package com.example.cloudclinic_said.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudclinic_said.ListDoctorAvailable;
import com.example.cloudclinic_said.R;

import java.util.List;

public class DoctorAvailableRecyclerViewAdapter extends RecyclerView.Adapter<DoctorAvailableRecyclerViewAdapter.DoctorAvailableViewHolder>{

    public List<ListDoctorAvailable> doctorAvailableList;
    private Context context;

    public DoctorAvailableRecyclerViewAdapter(Context context,List<ListDoctorAvailable> doctorAvailableList) {
        this.context=context;
        this.doctorAvailableList=doctorAvailableList;
    }

    @NonNull
    @Override
    public DoctorAvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View doctor_available_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_available_row,null);
        DoctorAvailableViewHolder availableDoctor = new DoctorAvailableViewHolder(doctor_available_row);
        return availableDoctor;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAvailableViewHolder holder, int position) {

        holder.tvDoctorName.setText(doctorAvailableList.get(position).getDoctorName());
//        holder.tvDoctorSpecialist.setText(doctorAvailableList.get(position).getDoctorSpeacialist());
//        holder.tvHospitalName.setText(doctorAvailableList.get(position).getHospitalName());
    }

    @Override
    public int getItemCount() {
        return doctorAvailableList.size();
    }

    public class DoctorAvailableViewHolder extends RecyclerView.ViewHolder{

        public TextView tvDoctorName;
        public TextView tvDoctorSpecialist;
        public TextView tvHospitalName;


        public DoctorAvailableViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tv_doctor_name_doctoravailable);
            tvDoctorSpecialist = itemView.findViewById(R.id.tv_doctor_specs_doctoravailable);
            tvHospitalName = itemView.findViewById(R.id.tv_hospital_name_doctoravailable);

        }
    }
}

