package com.nexign.service.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.service.Entity.CDR;
import com.nexign.service.Entity.CallDuration;
import com.nexign.service.Entity.UDR;
import com.nexign.service.Repository.CDRRepository;

@Service
public class UDRService {
    @Autowired
    private CDRRepository cdrRepository;

    public UDR getUDRForSubscriber(String msisdn, LocalDateTime startDate, LocalDateTime endDate) {
        List<CDR> cdrs = cdrRepository.findByCallerNumberAndStartTimeBetween(msisdn, startDate, endDate);
        return calculateUDR(msisdn, cdrs);
    }

    public List<UDR> getUDRsForAllSubscribers(LocalDateTime startDate, LocalDateTime endDate) {
        List<CDR> cdrs = cdrRepository.findByStartTimeBetween(startDate, endDate);
        return calculateUDRs(cdrs);
    }

    private UDR calculateUDR(String msisdn, List<CDR> cdrs) {
        long incomingDuration = 0;
        long outgoingDuration = 0;

        for (CDR cdr : cdrs) {
            if (cdr.getCallType().equals("02") && cdr.getReceiverNumber().equals(msisdn)) {
                incomingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).getSeconds();
            } else if (cdr.getCallType().equals("01") && cdr.getCallerNumber().equals(msisdn)) {
                outgoingDuration += Duration.between(cdr.getStartTime(), cdr.getEndTime()).getSeconds();
            }
        }

        return new UDR(msisdn, new CallDuration(formatDuration(incomingDuration)), new CallDuration(formatDuration(outgoingDuration)));
    }

    private List<UDR> calculateUDRs(List<CDR> cdrs) {
        Map<String, Long> incomingDurations = new HashMap<>();
        Map<String, Long> outgoingDurations = new HashMap<>();

        for (CDR cdr : cdrs) {
            String msisdn;
            long duration = Duration.between(cdr.getStartTime(), cdr.getEndTime()).getSeconds();

            if (cdr.getCallType().equals("02")) { 
                msisdn = cdr.getReceiverNumber();
                incomingDurations.put(msisdn, incomingDurations.getOrDefault(msisdn, 0L) + duration);
            } else if (cdr.getCallType().equals("01")) { 
                msisdn = cdr.getCallerNumber();
                outgoingDurations.put(msisdn, outgoingDurations.getOrDefault(msisdn, 0L) + duration);
            }
        }

        List<UDR> udrList = new ArrayList<>();
        for (String msisdn : incomingDurations.keySet()) {
            long incomingDuration = incomingDurations.get(msisdn);
            long outgoingDuration = outgoingDurations.getOrDefault(msisdn, 0L);
            udrList.add(new UDR(msisdn, new CallDuration(formatDuration(incomingDuration)), new CallDuration(formatDuration(outgoingDuration))));
        }

        for (String msisdn : outgoingDurations.keySet()) {
            if (!incomingDurations.containsKey(msisdn)) {
                long outgoingDuration = outgoingDurations.get(msisdn);
                udrList.add(new UDR(msisdn, new CallDuration("00:00:00"), new CallDuration(formatDuration(outgoingDuration))));
            }
        }

        return udrList;
    }

    private String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

}
