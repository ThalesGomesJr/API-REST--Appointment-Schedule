package Api.ClinicSchedule.controllers;

import Api.ClinicSchedule.model.Appointment;
import Api.ClinicSchedule.model.Patient;
import Api.ClinicSchedule.repository.AppointmentRepository;
import Api.ClinicSchedule.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientRepository _patientRepository;
    private final AppointmentRepository _appointmentRepository;

    @Autowired
    public PatientController(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {

        this._patientRepository = patientRepository;
        this._appointmentRepository= appointmentRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient register(@RequestBody @Valid Patient patient){
        return this._patientRepository.save(patient);
    }

    @PutMapping("{id}")
    public void update(@RequestBody @Valid Patient patientUpdated, @PathVariable Integer id){
        this._patientRepository.findById(id).map(patient -> {
           patientUpdated.setId(patient.getId());
           this._patientRepository.save(patientUpdated);
           return HttpStatus.OK;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        this._patientRepository.findById(id).map(patient -> {
            List<Appointment> listAppointment = this._appointmentRepository.listAppointments(patient.getId());
            for (Appointment appointment: listAppointment) {
                this._appointmentRepository.delete(appointment);
            }
            this._patientRepository.delete(patient);
            return HttpStatus.OK;
            
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
    }

    @GetMapping("{id}")
    public Patient getPatientById(@PathVariable Integer id){
        return this._patientRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
    }

    @GetMapping("all-patients")
    public List<Patient> AllPatients(){
        return this._patientRepository.findAll();
    }

    @PostMapping("{id}/add-appointment")
    public void addAppointment(@RequestBody @Valid Appointment appointment, @PathVariable Integer id){
        this._patientRepository.findById(id).map(patient -> {
            appointment.setPatient(patient);
            this._appointmentRepository.save(appointment);
            return HttpStatus.OK;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
    }

    @GetMapping("{id}/list-appointments")
    public List<Appointment> listAppointments(@PathVariable Integer id) {
       return this._appointmentRepository.listAppointments(id);
    }

}
