package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.infra.exception.ValidationExcept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationActiveDoctor implements AppointmentSchedulingValidation{

    @Autowired
    private DoctorRepository repository;

    public void validate (AppointmentScheduleData data) {
        if (data.idDoctor() == null) {
            return;
        }

        var doctorActive = repository.findActiveById(data.idDoctor());
        if (!doctorActive) {
            throw new ValidationExcept("Consultation cannot be scheduled with an inactive doctor.");
        }
    }
}
