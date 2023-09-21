package med.voll.api.domain.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String telephone;
    private String crm;
    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    public Doctor(DoctorData data) {
        this.name = data.name();
        this.email = data.email();
        this.telephone = data.telephone();
        this.crm = data.crm();
        this.active = true;
        this.specialty = data.specialty();
        this.address = new Address(data.address());
    }

    public void updateInformation(UpdateDoctorData data) {
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
