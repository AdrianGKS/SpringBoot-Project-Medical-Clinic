package med.voll.api.controller;

import med.voll.api.domain.appointment.AppointmentDetailData;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.appointment.SchedulingAppointments;
import med.voll.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentScheduleData> scheduleJson;

    @Autowired
    private JacksonTester<AppointmentDetailData> detailJson;

    @MockBean
    private SchedulingAppointments schedulingAppointments;

    @Test
    @DisplayName("Deve devolver http 400 quando infos estão inválidas")
    @WithMockUser
    void schedule_scenario1() throws Exception {
        var response = mvc.perform(post("/appointment")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver http 200 quando infos estão válidas")
    @WithMockUser
    void schedule_scenario2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;
        var detailsData = new AppointmentDetailData(null, 2l, 5l, date);

        when(schedulingAppointments.schedule(any())).thenReturn(detailsData);

        var response = mvc
                .perform(
                        post("/appointment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(scheduleJson.write(
                                        new AppointmentScheduleData(2l, 5l, date, specialty)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var json = detailJson.write(
                new AppointmentDetailData(null, 2l, 5l, date)
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(json);
    }
}