package med.voll.api.patient;

public record ListPatientData(
        Long id,
        String name,
        String email,
        String cpf
) {
    public ListPatientData(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
