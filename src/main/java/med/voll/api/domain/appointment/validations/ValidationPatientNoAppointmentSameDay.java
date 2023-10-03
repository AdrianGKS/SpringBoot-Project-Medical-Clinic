package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.ValidationExcept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationPatientNoAppointmentSameDay implements AppointmentSchedulingValidation{

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleData data) {
        var firstHour = data.date().withHour(7);
        var lastHour = data.date().withHour(18);
        var haveAppointment = repository.existsByPatientIdAndDateBetween(data.idPatient(), firstHour, lastHour);

        if (haveAppointment) {
            throw new ValidationExcept("Patient already has an appointment that day.");
        }
    }
}
