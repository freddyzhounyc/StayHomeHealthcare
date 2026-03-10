package com.stayhome.healthcare.repositories;

import com.stayhome.healthcare.domain.entities.AppointmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentLogRepository extends JpaRepository<AppointmentLog, UUID> {
}
