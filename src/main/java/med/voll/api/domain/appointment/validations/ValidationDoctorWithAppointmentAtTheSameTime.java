package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.ValidationExcept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationDoctorWithAppointmentAtTheSameTime implements AppointmentSchedulingValidation{

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleData data) {
        var haveConsultation = repository.existsByDoctorIdAndDateAndReasonCancellationIsNull(data.idDoctor(), data.date());

        if (haveConsultation) {
            throw new ValidationExcept("Doctor already has another appointment scheduled at that time");
        }
    }
}
