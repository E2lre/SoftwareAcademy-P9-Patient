package com.mediscreen.patient.tu;

import com.mediscreen.patient.dao.PatientDao;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase
public class PatientServiceTest {

    private static final Logger logger = LogManager.getLogger(PatientServiceTest.class);
    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientDao patientDao;

    private Patient patient;
    //constantes de test
    String firstNameConst = "Tatiana";
    String lastNameConst = "Romanova";
    String birthdateConst = "01/13/1693";
    String sexConst ="F";
    String addressConst = "10 Downing St";
    String phoneConst = "000-111-2222";

    @BeforeEach
    public void setUpEach() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date birthdate = simpleDateFormat.parse(birthdateConst);
            patient = new Patient();
            patient.setFirstName(firstNameConst);
            patient.setLastName(lastNameConst);
            patient.setBirthdate(birthdate);
            patient.setAddress(addressConst);
            patient.setSex(sexConst);
            patient.setPhone(phoneConst);

            List<Patient> patientList = new ArrayList<>();
            patientList.add(patient);


        } catch (ParseException e){
            logger.error(e.getMessage());
        }
    }

    /*------------------------ findAll ---------------------------------*/
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    //@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void findAll_WhenDBISNotEmpty_PatientLisIsReturn(){
        //GIVEN
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Mockito.when(patientDao.findAll()).thenReturn(patientList);

        //WHEN
        List<Patient> patientResultList =  patientService.findAll();
        //THEN
        assertThat(patientResultList).isNotEmpty();
    }

    /*------------------------ addPatient ---------------------------------*/
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    //@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void addPatient_inexistingPatientGiven_patientCreated(){

        //GIVEN
        Mockito.when(patientDao.existsByLastNameAndFirstName(lastNameConst,firstNameConst)).thenReturn(false);

        //WHEN
        boolean result =  patientService.addPatient(patient);
        //THEN
        assertThat(result).isTrue();
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    //@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void addPatient_existingPatientGiven_patientNotCreated(){

        //GIVEN
        Mockito.when(patientDao.existsByLastNameAndFirstName(lastNameConst,firstNameConst)).thenReturn(true);

        //WHEN
        boolean result =  patientService.addPatient(patient);
        //THEN
        assertThat(result).isFalse();
    }
}
