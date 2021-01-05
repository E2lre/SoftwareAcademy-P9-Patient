package com.mediscreen.patient.service;

import com.mediscreen.patient.config.security.JwtTokenProvider;
import com.mediscreen.patient.controller.exception.CustomException;
import com.mediscreen.patient.dao.UserDao;
import com.mediscreen.patient.model.Role;
import com.mediscreen.patient.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User findByUser(String username) {
        return userDao.findByUsername(username);
    }
// TODO deprecated
    @Override
    public boolean checkUser(User user) {
        boolean result = false;
        User userResult = userDao.findByUsername(user.getUsername());
        if (userResult!=null) {
            if (user.getPwd().equals(userResult.getPwd()))
            {
                logger.debug("user " + user.getUsername()+" is correctly identify");
                result = true;
            } else {
                logger.debug("user/pwd are incorrect for user " + user.getUsername());
                result = false;
            }
        } else {
            logger.debug("user " + user.getUsername() + " not found" );
            result = false;
        }

        return result;
    }
    @Override
    public String signin(User user) {
        logger.info("start");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPwd()));
            logger.info("finish");
            return jwtTokenProvider.createToken(user.getUsername(), userDao.findByUsername(user.getUsername()).getRoles());
        } catch (AuthenticationException e) {

            logger.error("Invalid username/password supplied for " + user.getUsername());
            return "";
        }
    }

    @Override
    public String addUser(User user) {
        boolean result = false;
        logger.info("start");
        if (!userDao.existsByUsername(user.getUsername())) {
            user.setPwd(passwordEncoder.encode(user.getPwd()));
            user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
            User resultUser = userDao.save(user);
            if (resultUser != null) {
                    result = true;
            }
        }
        if (result) {
            logger.info("start");
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
