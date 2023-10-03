package med.voll.api.domain.appointment.cancellations;

import med.voll.api.domain.appointment.AppointmentCancellationData;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.infra.exception.ValidationExcept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceTimeValidation implements AppointmentCancellingValidation{

    @Autowired
    private AppointmentRepository repository;

    @Override
    public void validate(AppointmentCancellationData data) {
        var appointment = repository.getReferenceById(data.id());
        var now = LocalDateTime.now();
        var diference = Duration.between(now, appointment.getDate()).toHours();

        if (diference < 24) {
            throw new ValidationExcept("Consultation can only be canceled at least 24 hours in advance!");
        }
    }
}
