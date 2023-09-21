package med.voll.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressData;

public record UpdatePatientData(
        @NotNull
        Long id,
        String name,
        String telephone,
        @Valid
        AddressData address
) {
}
