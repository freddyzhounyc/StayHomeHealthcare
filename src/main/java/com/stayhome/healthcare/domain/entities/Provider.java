package com.stayhome.healthcare.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_id", updatable = false, nullable = false)
    private UUID providerId;

    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @OneToMany(mappedBy = "provider")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "provider")
    private List<AppointmentLog> appointmentLogsList;

}
