package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.model.User;

public interface UserService {
    /**
     * find a user
     * @param username user id
     * @return user
     */
    public User findByUser(String username);

    /**
     * check if a user exist
     * @param user user to be check
     * @return true if check
     */
    public boolean checkUser (User user);

    /**
     * identifier a user
     * @param user user to be identify
     * @return JWT tocken if user is identify
     */
    public String signin(User user);

    /**
     * create a new user
     * @param user user to be create
     * @return JWT tocken
     */
    public String addUser(User user);

    /**
     * update password for a user
     * @param user user to be update whith new password
     * @return user updated
     */
    public User updateUser(User user);

    /**
     * delete a user
     * @param user user to be delete
     * @return deleted user
     */
    public User deleteUser(User user);
}
