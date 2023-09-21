package med.voll.api.domain.doctor;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.Specialty;

public record DetailDoctorData(
        Long id,
        String name,
        String email,
        String crm,
        String telephone,
        Specialty specialty,
        Address address
) {
    public DetailDoctorData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getTelephone(), doctor.getSpecialty(), doctor.getAddress());
    }
}
