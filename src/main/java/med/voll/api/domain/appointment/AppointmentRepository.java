package med.voll.api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDateAndReasonCancellationIsNull(Long aLong, LocalDateTime date);

    boolean existsByPatientIdAndDateBetween(Long aLong, LocalDateTime firstHour, LocalDateTime lastHour);
}
