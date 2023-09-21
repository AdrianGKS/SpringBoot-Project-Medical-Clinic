package med.voll.api.domain.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "patient")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String telephone;
    private String cpf;
    private Boolean active;

    @Embedded
    private Address address;

    public Patient(PatientData data) {
        this.name = data.name();
        this.email = data.email();
        this.telephone = data.telephone();
        this.active = true;
        this.address = new Address(data.address());
    }

    public void updateInformation(UpdatePatientData data) {
        if (data.name() != null)
            this.name = data.name();

        if (data.telephone() != null)
            this.telephone = data.telephone();

        if (data.address() != null)
            this.address.updateInformation(data.address());
    }

    public void disable() {
        this.active = false;
    }
}
