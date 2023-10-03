package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.ValidationExcept;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidationScheduleAdvance implements AppointmentSchedulingValidation{

    public void validate (AppointmentScheduleData data) {
        var dateAppointment = data.date();
        var now = LocalDateTime.now();
        var diferenceTime = Duration.between(now, dateAppointment).toMinutes();

        if (diferenceTime < 30) {
            throw new ValidationExcept("Appointments must be made 30 minutes in advance.");
        }
    }
}
