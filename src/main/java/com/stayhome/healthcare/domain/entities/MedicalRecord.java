package com.stayhome.healthcare.domain.entities;

import com.stayhome.healthcare.domain.entities.enums.MedicalExamType;
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
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "medical_record_id", updatable = false, nullable = false)
    private UUID medicalRecordId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "medical_exam_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicalExamType medicalExamType;

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
