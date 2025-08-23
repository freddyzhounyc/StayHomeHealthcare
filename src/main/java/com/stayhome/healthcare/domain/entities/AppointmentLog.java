package com.stayhome.healthcare.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment_logs")
public class AppointmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "appointment_log_id", updatable = false, nullable = false)
    private UUID appointmentLogId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "start_time_date", nullable = false)
    private LocalDateTime startTimeDate;

    @Column(name = "end_time_date", nullable = false)
    private LocalDateTime endTimeDate;

}
