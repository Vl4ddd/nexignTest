package com.nexign.service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.service.Entity.CDR;

public interface CDRRepository extends JpaRepository<CDR, Long> {
}