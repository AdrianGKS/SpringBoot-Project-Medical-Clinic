package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentDetailData;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.appointment.SchedulingAppointments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointment")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private SchedulingAppointments appointments;

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid AppointmentScheduleData data) {
        var dto = appointments.schedule(data);

        return ResponseEntity.ok(dto);
    }
}
