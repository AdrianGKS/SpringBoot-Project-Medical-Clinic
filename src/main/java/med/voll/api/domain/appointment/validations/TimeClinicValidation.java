package med.voll.api.domain.appointment.validations;


import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.infra.exception.ValidationExcept;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class TimeClinicValidation implements AppointmentSchedulingValidation{

    public void validate (AppointmentScheduleData data) {
        var dateAppointment = data.date();

        var sunday = dateAppointment.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpen = dateAppointment.getHour() < 7;
        var afterClose = dateAppointment.getHour() > 18;

        if (sunday || beforeOpen || afterClose) {
            throw new ValidationExcept("Consultation outside opening hours");
        }
    }
}
