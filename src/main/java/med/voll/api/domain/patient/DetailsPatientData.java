package med.voll.api.domain.patient;

import med.voll.api.domain.address.Address;

public record DetailsPatientData(
        String name,
        String email,
        String telephone,
        String cpf,
        Address address
) {
    public DetailsPatientData(Patient patient) {
        this(patient.getName(), patient.getEmail(), patient.getTelephone(), patient.getCpf(), patient.getAddress());
    }
}
