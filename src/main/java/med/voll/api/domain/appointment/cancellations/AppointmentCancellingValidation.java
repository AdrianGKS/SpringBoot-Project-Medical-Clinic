package med.voll.api.domain.appointment.cancellations;

import med.voll.api.domain.appointment.AppointmentCancellationData;

public interface AppointmentCancellingValidation {
    void validate(AppointmentCancellationData dados);

}
