package com.mediscreen.patient.controller;

import com.mediscreen.patient.controller.exception.PatientCanNotbeAddedException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.model.dto.PatientDto;
import com.mediscreen.patient.service.PatientService;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PatientController {
    private static final Logger logger = LogManager.getLogger(PatientController.class);
    @Autowired
    private PatientService patientService;

    /*---------------------------  GET Find All -----------------------------*/
    @GetMapping(value = "patients")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> listPatients()  {
        logger.info("listPatients start/finish");
        return patientService.findAll();

    }
    /*---------------------------  POST Patient -----------------------------*/
    @PostMapping(value = "patient")
    @ResponseStatus(HttpStatus.CREATED)
    public String addPatient(@RequestBody Patient patient) throws PatientCanNotbeAddedException {
        String finalResult;
        logger.info("addPatients start");
        boolean result = patientService.addPatient(patient);
        if (result) {
            finalResult = "The patient " + patient.getId() + " has been created";
        } else {
            finalResult = "The patient " + patient.getId() + " already exist";
            throw new PatientCanNotbeAddedException("The patient " + patient.getId() + " already exist");
        }
        return finalResult;
    }

}
