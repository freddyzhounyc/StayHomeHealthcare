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
@Table(name = "medicine_lists")
public class MedicineList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "medicine_list_id", updatable = false, nullable = false)
    private UUID medicineListId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "medication", nullable = false)
    private String medication;

    @Column(name = "dosage_measurement", nullable = false)
    private String dosageMeasurement;

    @Column(name = "dosage", nullable = false)
    private Double dosage;

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
