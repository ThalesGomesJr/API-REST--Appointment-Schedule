package Api.ClinicSchedule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", nullable = false)
    @NotEmpty(message = "{field.username.mandatory}")
    private String userName;

    @Column(name = "full_name",nullable = false)
    @NotEmpty(message = "{field.fullname.mandatory}")
    private String fullName;

    @Column(nullable = false,length = 11)
    @NotEmpty(message = "{field.cpf.mandatory}")
    @CPF(message = "{field.cpf.invalid}")
    private String cpf;

    @Column(nullable = false)
    @NotEmpty(message = "{field.genre.mandatory}")
    private String genre;

    @Column
    private Integer ageYears;

    @Column(name = "date_birth", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "{field.datebirth.mandatory}")
    private LocalDate dateBirth;

    @Column(nullable = false)
    @NotEmpty(message = "{field.email.mandatory}")
    @Email(message = "{field.email.invalid}")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "{field.telephone.mandatory}")
    private String telephone;

    @Column(name = "date_registration", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateRegistration;

    @PrePersist
    public void prePersist(){
        //Adiciona a data de registro
        setDateRegistration(LocalDateTime.now());
        //Calcula e adiciona a idade
        setAgeYears(Period.between(getDateBirth(), LocalDate.now()).getYears());

    }


}
