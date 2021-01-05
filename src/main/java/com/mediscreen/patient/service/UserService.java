package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.model.User;

public interface UserService {
    public User findByUser(String username);
    public boolean checkUser (User user);
    public String signin(User user);
    public String addUser(User user);
}
