package com.nexign.service.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.service.Service.CDRReportService;
import com.nexign.service.Service.CdrGeneratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Контроллер для генерации CDR.
 * Предоставляет методы для генерации и записи данных в БД.
 * @author Влад 
 */
@RestController
@RequestMapping("/api/v1/cdr")
public class CDRController {

    /** Сервис для работы с CDR записями */
    @Autowired
    private CdrGeneratorService cdrGeneratorService;

    /** Сервис для работы с CDR отчетами по абоненту */
    @Autowired
    private CDRReportService cdrReportService;

    /**
    * Создает и записывает в БД записи CDR.
    *
    * @return Результат работы метода (успех/провал).
    */
    @Operation(summary = "Сгенерировать и занести данные о звонках абонентов",
               description = "Генерирует записи и вносит их в базу данных.",
               tags = {"CDR"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно созданы записи"),
        @ApiResponse(responseCode = "400", description = "Ошибка записи"),
    })   
    @PostMapping("/generate")
    public String generateCDR() {
        cdrGeneratorService.generateCDR(1000);
        return "CDR созданы";
    }


    /**
    * Создает Отчет CDR по абоненту.
    *
    * @return Результат работы метода (успех/провал).
    */
    @Operation(summary = "Создать отчет по абоненту",
               description = "Создает отчет по абоненту txt файл.",
               tags = {"CDR"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно создан отчет"),
        @ApiResponse(responseCode = "400", description = "Ошибка созданя"),
    })   
    @PostMapping("/createReport")
    public String createCDRReport(@Parameter(description = "Номер абонента", required = true) 
            @RequestParam String msisdn,
            @Parameter(description = "Дата начала периода (формат: yyyy-MM-dd'T'HH:mm:ss)", required = false)
            @Schema(format = "date-time", example = "2023-10-01T00:00:00")
            @RequestParam(required = false) String startDate,
            @Parameter(description = "Дата окончания периода (формат: yyyy-MM-dd'T'HH:mm:ss)", required = false)
            @Schema(format = "date-time", example = "2026-10-01T00:00:00")
            @RequestParam(required = false) String endDate) {

        LocalDateTime start;
        LocalDateTime end; 
        if (startDate != null && endDate != null) {
            start = LocalDateTime.parse(startDate);;
            end = LocalDateTime.parse(endDate);
        } else {
            start = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        }
        cdrReportService.generateCDRReport(msisdn, start, end);
        return "CDR отчет создан";
    }

}
