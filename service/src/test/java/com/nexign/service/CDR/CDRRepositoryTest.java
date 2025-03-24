package com.nexign.service.CDR;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.nexign.service.Entity.CDR;
import com.nexign.service.Repository.CDRRepository;



@DataJpaTest
public class CDRRepositoryTest {

    @Autowired
    private CDRRepository cdrRepository;

    @Test
    @Transactional 
    void testCreateCDR() {
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 1, 12, 5);
        CDR cdr = new CDR("01", "123456789", "987654321", startTime, endTime);

        CDR savedCDR = cdrRepository.save(cdr);

        assertEquals(cdr.getCallType(), savedCDR.getCallType());
        assertEquals(cdr.getCallerNumber(), savedCDR.getCallerNumber());
        assertEquals(cdr.getReceiverNumber(), savedCDR.getReceiverNumber());
        assertEquals(cdr.getStartTime(), savedCDR.getStartTime());
        assertEquals(cdr.getEndTime(), savedCDR.getEndTime());
        assertEquals(cdr.getId(), savedCDR.getId()); 
    }

}
