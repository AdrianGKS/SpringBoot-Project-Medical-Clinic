package med.voll.api.domain.doctor;

import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorData;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.Specialty;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientData;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager manager;

    @Test
    @DisplayName("Deve devolver null quando unico médico cadastrado não está disponível na data")
    void chooseRandomDoctorByFreeDateScenario1() {
        var nextMonday10PM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 10);

        var doctor = registerDoctor("Lúcio", "medico@voll.med", "221453", Specialty.CARDIOLOGY);
        var patient = registerPatient("Alice", "paciente@email.com", "11122233345");
        registerAppointment(doctor, patient, nextMonday10PM);

        var freeDoctor = doctorRepository.chooseRandomDoctorByFreeDate(Specialty.CARDIOLOGY, nextMonday10PM);
        AssertionsForClassTypes.assertThat(freeDoctor).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void chooseRandomDoctorByFreeDateScenario2() {
        var nextMonday10PM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 10);

        var doctor = registerDoctor("Lúcio", "medico@voll.med", "956485", Specialty.GYNECOLOGY);

        var freeDoctor = doctorRepository.chooseRandomDoctorByFreeDate(Specialty.GYNECOLOGY, nextMonday10PM);
        AssertionsForClassTypes.assertThat(freeDoctor).isEqualTo(doctor);
    }

    private void registerAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        manager.persist(new Appointment(null, doctor, patient, date, null));
    }

    private Doctor registerDoctor(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(doctorData(name, email, crm, specialty));
        manager.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        manager.persist(patient);
        return patient;
    }

    private DoctorData doctorData(String name, String email, String crm, Specialty specialty) {
        return new DoctorData (
                name,
                email,
                "61999999999",
                crm,
                specialty,
                addressData()
        );
    }

    private PatientData patientData(String name, String email, String cpf) {
        return new PatientData(
                name,
                email,
                cpf,
                "61999999999",
                addressData()
        );
    }

    private AddressData addressData() {
        return new AddressData(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}