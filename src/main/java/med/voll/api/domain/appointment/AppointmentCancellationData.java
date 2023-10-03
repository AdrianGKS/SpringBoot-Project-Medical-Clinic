package med.voll.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentCancellationData(
        @NotNull
        Long id,

        @NotNull
        ReasonCancellation reason
) {
}
