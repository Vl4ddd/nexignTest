package com.nexign.service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.service.Entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
