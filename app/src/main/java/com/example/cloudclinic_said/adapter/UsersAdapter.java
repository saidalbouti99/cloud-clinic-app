package com.example.cloudclinic_said.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudclinic_said.ListDoctorAvailable;
import com.example.cloudclinic_said.R;
import com.example.cloudclinic_said.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private List<ListDoctorAvailable>users;

    public UsersAdapter(List<ListDoctorAvailable> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.book_doctor_test_row,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView doctor_name;
        ImageView img_call_test;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name=itemView.findViewById(R.id.tv_doctor_name_test);
            img_call_test=itemView.findViewById(R.id.img_call_test);
        }

        void setUserData(ListDoctorAvailable user){
//            doctor_name.setText(user.DoctorName);
        }
    }
}
