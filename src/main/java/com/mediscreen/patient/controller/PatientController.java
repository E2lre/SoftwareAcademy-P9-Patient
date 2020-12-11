package com.mediscreen.patient.controller;

import com.mediscreen.patient.controller.exception.PatientCanNotBeDeleteException;
import com.mediscreen.patient.controller.exception.PatientCanNotBeSavedException;
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
    /*---------------------------  GET by id -----------------------------*/
    @GetMapping(value = "patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatientById(@PathVariable long id) {
        logger.info("listPatients start/finish-"+id);
        return patientService.findById(id);

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
            logger.warn(finalResult);
            throw new PatientCanNotbeAddedException("The patient " + patient.getId() + " already exist");
        }
        return finalResult;
    }

    /*---------------------------  PUT Patient -----------------------------*/
    @PutMapping(value = "patient")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Patient updatePatient(@RequestBody Patient patient) throws PatientCanNotBeSavedException {
        Patient finalResult = null;
        logger.info("savePatients start");
        finalResult = patientService.updatePatient(patient);
        if (finalResult == null) {

            logger.warn("The patient " + patient.getId() + " does not exist");
            throw new PatientCanNotBeSavedException("The patient " + patient.getId() + " does not exist");
        }
        return finalResult;
    }
    /*---------------------------  DEL Patient -----------------------------*/

    /*
     * Delete Patient
     * Give matint Id in Url
     * @param patient Patient to be delete. it is check with Id in Url
     * @return
     * @throws PatientCanNotBeSavedException
     */

    /**
     * Delete Patient
     * @param id Patient Id to be delete
     * @param patient Patient to be delete : it is for check with ID
     * @return patient deleted
     * @throws PatientCanNotBeSavedException exception if db error or if patient is not correspond to the id
     */
    @DeleteMapping(value = "patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient deletePatient(@PathVariable long id,@RequestBody Patient patient) throws PatientCanNotBeDeleteException {
        Patient finalResult = null;
        logger.info("deletePatients start");
        finalResult = patientService.deletePatient(id,patient);
        if (finalResult == null) {

            logger.warn("The patient " + patient.getId() + " can not be delete");
            throw new PatientCanNotBeDeleteException("The patient " + patient.getId() + " can not be delete");
        }
        return finalResult;
    }
}
