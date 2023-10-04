package med.voll.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.address.AddressData;

public record PatientData(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String cpf,

        @NotBlank
        String telephone,

        @NotBlank
        @Valid
        AddressData address
) {
}
