package com.nexign.service.CDR;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.nexign.service.Entity.CDR;

public class CDREntityTest {

    @Test
    void testCDRConstructorAndGetters() {
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 1, 12, 5);
        CDR cdr = new CDR("01", "123456789", "987654321", startTime, endTime);

        assertEquals("01", cdr.getCallType());
        assertEquals("123456789", cdr.getCallerNumber());
        assertEquals("987654321", cdr.getReceiverNumber());
        assertEquals(startTime, cdr.getStartTime());
        assertEquals(endTime, cdr.getEndTime());
    }

    
}
