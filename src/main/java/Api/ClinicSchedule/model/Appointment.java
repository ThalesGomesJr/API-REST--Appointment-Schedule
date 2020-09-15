package Api.ClinicSchedule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String specialty;

    @Column(name = "date_time", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String status;

    @Column(name = "request_date", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime requestDate;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @PrePersist
    public void prePersist(){
        //Adiciona a data de solicitação da consulta
        setRequestDate(LocalDateTime.now());
    }
}
