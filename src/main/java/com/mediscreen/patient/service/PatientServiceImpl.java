package com.mediscreen.patient.service;

import com.mediscreen.patient.dao.PatientDao;
import com.mediscreen.patient.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private static final Logger logger = LogManager.getLogger(PatientServiceImpl.class);
    @Autowired
    private PatientDao patientDao;
    @Override
    public List<Patient> findAll() {
        logger.info("Start/finish");
        List<Patient> resultPatients = patientDao.findAll();
        logger.info("size:"+resultPatients.size());
        //return patientDao.findAll();
        return resultPatients;
    }

/*    @Override
    public Patient findById(Integer id) {
   *//*     logger.info("Start/finish pour id"+id.toString());
        //Patient resultPatient = patientDao.findById(id);
        logger.info("size:"+resultPatient.size());
        //return patientDao.findAll();
        return resultPatient;*//*
        return null;
    }*/
}
