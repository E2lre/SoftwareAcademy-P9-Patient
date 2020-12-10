package com.mediscreen.patient.ti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.dao.PatientDao;
import com.mediscreen.patient.model.Patient;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Column;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PatientControllerIT {
    private static final Logger logger = LogManager.getLogger(PatientControllerIT.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PatientDao patientDao;
    //constantes de test
    String inexistingFirstnameConst = "James";
    String inexistingLastnameConst = "Bond";
    String birthdate ="01/13/1693";
    Date birthdateConst ;
    String sexConst = "M";
    String addressConst = "10 downing St";
    String phoneConst = "0123456789";

    String existingFirstnameConst = "Hubert";
    String existingLastnameConst = "Bonisseur de la Bath";
    @BeforeEach
    public void setUpEach() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            birthdateConst = simpleDateFormat.parse(birthdate);
        } catch (ParseException e){
            logger.error(e.getMessage());
        }
    }

    /*---------------------------------------- GET Find All -------------------------------*/
    @Test
    //@WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void listPatients_existingPatients_patientListIsDone() throws Exception {
        //Given

        //WHEN THEN
        mockMvc.perform(get("/patients/"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    /*---------------------------------------- POST Add Patient -------------------------------*/
    @Test
    //@WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addPatient_inExistingPatient_patientIsCreate() throws Exception {
        //Given
        Patient patient = new Patient();
        patient.setFirstName(inexistingFirstnameConst);
        patient.setLastName(inexistingLastnameConst);
        patient.setBirthdate(birthdateConst);
        patient.setSex(sexConst);
        patient.setAddress(addressConst);
        patient.setPhone(phoneConst);


        //WHEN THEN
        mockMvc.perform(post("/patient")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    //@WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addPatient_ExistingPatient_errorIsReturn() throws Exception {
        //Given
        Patient patient = new Patient(); //10,inexistingFirstnameConst,inexistingLastnameConst,birthdateConst,sexConst,addressConst,phoneConst);
        patient.setFirstName(existingFirstnameConst);
        patient.setLastName(existingLastnameConst);
        patient.setBirthdate(birthdateConst);
        patient.setSex(sexConst);
        patient.setAddress(addressConst);
        patient.setPhone(phoneConst);


        //WHEN THEN
        mockMvc.perform(post("/patient")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    /*---------------------------------------- PUT update Patient -------------------------------*/
    @Test
    //@WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatePatient_existingIdPatient_patientIsUpdate() throws Exception {
        //Given
        Patient patient = new Patient();
        patient.setId(0);
        patient.setFirstName(existingFirstnameConst);
        patient.setLastName(existingLastnameConst);
        patient.setBirthdate(birthdateConst);
        patient.setSex(sexConst);
        patient.setAddress(addressConst);
        patient.setPhone(phoneConst);


        //WHEN THEN
        mockMvc.perform(put("/patient")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }
    @Test
    //@WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updatePatient_inexistingIdPatient_errorIsReturn() throws Exception {
        //Given
        Patient patient = new Patient();
        patient.setId(999);
        patient.setFirstName(existingFirstnameConst);
        patient.setLastName(existingLastnameConst);
        patient.setBirthdate(birthdateConst);
        patient.setSex(sexConst);
        patient.setAddress(addressConst);
        patient.setPhone(phoneConst);


        //WHEN THEN
        mockMvc.perform(put("/patient")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    /*---------------------------------------- Utility -------------------------------*/
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
