package med.voll.api.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.address.AddressData;

public record UpdatePatientData(
        @NotNull
        Long id,
        String name,
        String telephone,
        @Valid
        AddressData address
) {
}
