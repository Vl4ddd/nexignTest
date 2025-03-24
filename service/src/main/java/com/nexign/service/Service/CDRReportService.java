package com.nexign.service.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.service.Entity.CDR;
import com.nexign.service.Repository.CDRRepository;

/**
 * Service для создания отчетов CDR по абоненту.
 * Предоставляет методы для создания отчетов.
 * @author Влад 
 */
@Service
public class CDRReportService {

    /** Репозиторий для работы с CDR записями */
    @Autowired
    private CDRRepository cdrRepository;


 /**
    * Получает номер абонента и промежуток по звонкам и создает на их основе отчет CDR.
    *
    * @param msisdn номер абонента.
    * @param startDate дата, с которой будет произведен поиск.
    * @param endDate дата, которой будет окончен поиск.
    */
    public void generateCDRReport(String msisdn, LocalDateTime startDate, LocalDateTime endDate){
        List<CDR> cdrs = cdrRepository.findByCallerNumberAndStartTimeBetween(msisdn, startDate, endDate);

        UUID uniqueKey = UUID.randomUUID();

        Path reportsDir = Paths.get("nexignTest/service/src/main/resources/reports");

        try {
            if (!Files.exists(reportsDir)) {
                Files.createDirectory(reportsDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; 
        }

        String uniqueFileName = "nexignTest/service/src/main/resources/reports/" + msisdn + "_" + uniqueKey.toString();

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
