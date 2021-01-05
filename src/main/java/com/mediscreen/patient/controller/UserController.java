package com.mediscreen.patient.controller;

import com.mediscreen.patient.controller.exception.UserSignupException;
import com.mediscreen.patient.controller.exception.UserNotFoundException;
import com.mediscreen.patient.model.User;
import com.mediscreen.patient.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /*---------------------------  GET Find All -----------------------------*/
    @GetMapping(value = "signin")
    @ResponseStatus(HttpStatus.OK)
    public String signin(@RequestParam(name = "username") String username,@RequestParam(name = "pwd") String pwd) throws UserNotFoundException {
    //public boolean signin(@RequestParam(name = "username") String username) throws UserNotFoundException {
      //  String pwd = "pwd";
        logger.info("signin start/finish");
        User userIn =new User(username,pwd);
        String result = userService.signin(userIn);
        if ((result == null) || (result.isEmpty())) {
            logger.warn("The user " + username + " with password is incorrect");
            throw new UserNotFoundException("The user " + username + " with password is incorrect");

        }
        return result;
    }
    /*    @GetMapping(value = "signin")
    @ResponseStatus(HttpStatus.OK)
    public boolean signin(@RequestParam(name = "username") String username,@RequestParam(name = "pwd") String pwd) throws UserNotFoundException {
    //public boolean signin(@RequestParam(name = "username") String username) throws UserNotFoundException {
      //  String pwd = "pwd";
        logger.info("signin start/finish");
        User userIn =new User(username,pwd);
        boolean result = true;
        if (!userService.checkUser(userIn)) {
            logger.warn("The user " + username + " with password is incorrect");
            throw new UserNotFoundException("The user " + username + " with password is incorrect");

        }
        return result;
    }*/
    /*---------------------------  Post Signup-----------------------------*/

    @PostMapping(value="/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestParam(name = "username") String username,@RequestParam(name = "pwd") String pwd) throws UserSignupException {
        User user;
        String result = null;
        logger.info("signup start");
        if ((!username.isEmpty()) && (!pwd.isEmpty())){
            user = new User(username,pwd);
            result = userService.addUser(user);
        }

        if (result == null) {
            throw new UserSignupException(" Impossible to Signup username " + username);
        }
        logger.info("signup finish");
        return result;
    }
}
