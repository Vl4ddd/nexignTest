package com.nexign.service.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.service.Entity.CDR;

public interface CDRRepository extends JpaRepository<CDR, Long> {
    List<CDR> findByCallerNumberAndStartTimeBetween(String callerNumber, LocalDateTime startTime, LocalDateTime endTime);
    List<CDR> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
