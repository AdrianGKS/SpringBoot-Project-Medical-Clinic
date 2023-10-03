package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentScheduleData;

public interface AppointmentSchedulingValidation {

    void validate(AppointmentScheduleData data);
}
