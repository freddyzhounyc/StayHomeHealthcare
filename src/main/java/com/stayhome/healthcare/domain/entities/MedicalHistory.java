package com.stayhome.healthcare.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "medical_histories")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "medical_history_id", updatable = false, nullable = false)
    private UUID medicalHistoryId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "body_system", nullable = false)
    @Enumerated(EnumType.STRING)
    private BodySystem bodySystem;

    @Column(name = "body_description", nullable = false)
    private String bodyDescription;

    @Column(name = "created", nullable = false)
    private LocalDate created;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDate.now();
        this.lastUpdated = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

}
