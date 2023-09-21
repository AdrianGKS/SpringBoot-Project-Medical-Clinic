package med.voll.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import med.voll.api.domain.address.AddressData;

public record PatientData(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telephone,

        @NotBlank
        String cpf,

        @NotBlank
        @Valid
        AddressData address
) {
}
