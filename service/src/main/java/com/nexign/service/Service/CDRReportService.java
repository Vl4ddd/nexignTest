package com.nexign.service.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexign.service.Entity.CDR;
import com.nexign.service.Repository.CDRRepository;

public class CDRReportService {

    /** Репозиторий для работы с CDR записями */
    @Autowired
    private CDRRepository cdrRepository;



    public void generateCDRReport(String msisdn, LocalDateTime startDate, LocalDateTime endDate){
        List<CDR> cdrs = cdrRepository.findByCallerNumberAndStartTimeBetween(msisdn, startDate, endDate);

        UUID uniqueKey = UUID.randomUUID();
        
        String uniqueFileName = msisdn + "_" + uniqueKey.toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(uniqueFileName))) {
            for (CDR cdr : cdrs) {
                writer.write(cdr.toString()); 
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    


}
