package Api.ClinicSchedule.repository;

import Api.ClinicSchedule.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    //Returna as consultas marcadas
    @Query(value = " SELECT * FROM appointment WHERE id_patient = ?1", nativeQuery = true)
    List<Appointment> listAppointments(Integer id);
}
