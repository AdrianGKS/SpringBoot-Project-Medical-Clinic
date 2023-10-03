package med.voll.api.domain.appointment;

import med.voll.api.domain.appointment.cancellations.AppointmentCancellingValidation;
import med.voll.api.domain.appointment.validations.AppointmentSchedulingValidation;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.infra.exception.ValidationExcept;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulingAppointments {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<AppointmentSchedulingValidation> validations;

    @Autowired
    private List<AppointmentCancellingValidation> cancellations;

    public AppointmentDetailData schedule(AppointmentScheduleData data) {
        if (!patientRepository.existsById(data.idPatient())) {
            throw new ValidationExcept("Patient ID not exist");
        }
        if (data.idDoctor() != null && !doctorRepository.existsById(data.idDoctor())) {
            throw new ValidationExcept("Doctor ID not exist");
        }

        validations.forEach(v -> v.validate(data));

        var patient = patientRepository.getReferenceById(data.idPatient());
        var doctor = chooseDoctor(data);

        if (doctor == null) {
            throw new ValidationExcept("There is no doctor available on that date.");
        }

        var appointment = new Appointment(null, doctor, patient, data.date(), null);

        appointmentRepository.save(appointment);

        return new AppointmentDetailData(appointment);
    }

    private Doctor chooseDoctor(AppointmentScheduleData data) {
        if (data.idDoctor() != null) {
            return doctorRepository.getReferenceById(data.idDoctor());
        }
        if (data.specialty() == null) {
            throw new ValidationExcept("Specialty is mandatory if you do not choose a doctor.");
        }

        return doctorRepository.chooseRandomDoctorByFreeDate(data.specialty(), data.date());
    }

    public void cancel(AppointmentCancellationData data) {
        if (!appointmentRepository.existsById(data.id())) {
            throw new ValidationExcept("Id da consulta informado não existe!");
        }

        cancellations.forEach(v -> v.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.id());
        appointment.reason(data.reason());
    }
}
