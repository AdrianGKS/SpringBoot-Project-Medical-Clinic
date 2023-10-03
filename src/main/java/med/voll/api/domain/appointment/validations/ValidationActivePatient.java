package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.infra.exception.ValidationExcept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationActivePatient implements AppointmentSchedulingValidation{

    @Autowired
    private PatientRepository repository;

    public void validate(AppointmentScheduleData data) {
        var isActive = repository.findActiveById(data.idPatient());

        if (!isActive) {
            throw new ValidationExcept("Consultation cannot be scheduled with an inactive patient.");
        }
    }
}
