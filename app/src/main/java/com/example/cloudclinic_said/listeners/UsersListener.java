package com.example.cloudclinic_said.listeners;

import com.example.cloudclinic_said.models.User;

public interface UsersListener {

    void  initiateVideoMeeting(User user);
    void  initiateAudioMeeting(User user);

}
