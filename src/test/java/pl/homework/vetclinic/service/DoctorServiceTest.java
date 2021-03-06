package pl.homework.vetclinic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.homework.vetclinic.BaseTest;
import pl.homework.vetclinic.model.Appointment;
import pl.homework.vetclinic.model.Doctor;

import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@SpringBootTest
class DoctorServiceTest extends BaseTest {
    
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private CustomerService customerService;
    @Test
    void saveAndDeleteDoctor(){
        //clear the table
        doctorService.deleteAllDoctors();

        //given
        Doctor firstDoctor = getFirstDoctor();
        Doctor secondDoctor = getSecondDoctor();
        
        //when
        doctorService.saveDoctor(firstDoctor);
        doctorService.saveDoctor(secondDoctor);
        
        //then
        //check if the table contains two items
        assertEquals(2, StreamSupport.stream(doctorService.getAllDoctors().spliterator(), false).count());

        //then
        assertEquals(firstDoctor, doctorService.getDoctorById(firstDoctor.getId()).get());
        assertNotEquals(secondDoctor, doctorService.getDoctorById(firstDoctor.getId()).get());

        //delete
        doctorService.deleteDoctorById(firstDoctor.getId());

        //check if the table contains one items
        assertTrue(doctorService.getDoctorById(firstDoctor.getId()).isEmpty());
        assertEquals(1, StreamSupport.stream(doctorService.getAllDoctors().spliterator(), false).count());

        //then
        assertEquals(secondDoctor,doctorService.getDoctorById(secondDoctor.getId()).get());
        assertNotEquals(firstDoctor, doctorService.getDoctorById(secondDoctor.getId()).get());

        //clear the table
        doctorService.deleteAllDoctors();
    }

    @Test
    void getDoctorById(){
        clearTables();

        //given
        Doctor doctor = getFirstDoctor();

        //then
        doctorService.saveDoctor(doctor);
        assertEquals(doctor, doctorService.getDoctorById(doctor.getId()).get());

        clearTables();
    }
}
