package Api.ClinicSchedule.repository;

import Api.ClinicSchedule.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
