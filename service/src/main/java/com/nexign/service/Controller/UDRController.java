package com.nexign.service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexign.service.Entity.UDR;
import com.nexign.service.Service.UDRService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/udr")
public class UDRController {

    @Autowired
    private UDRService udrService;

    @Operation(summary = "Получить UDR запись для абонента",
               description = "Возвращает UDR запись для указанного абонента за запрашиваемый период.",
               tags = {"UDR"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно получена UDR запись"),
        @ApiResponse(responseCode = "400", description = "Неверные параметры запроса"),
        @ApiResponse(responseCode = "404", description = "Абонент не найден")
    })           
    @GetMapping("/subscriber")
    public ResponseEntity<UDR> getUDRForSubscriber(
            @Parameter(description = "Номер абонента", required = true) 
            @RequestParam String msisdn,
            @Parameter(description = "Дата начала периода (формат: yyyy-MM-dd'T'HH:mm:ss)", required = false)
            @Schema(format = "date-time", example = "2023-10-01T00:00:00")
            @RequestParam(required = false) String startDate,
            @Parameter(description = "Дата окончания периода (формат: yyyy-MM-dd'T'HH:mm:ss)", required = false)
            @Schema(format = "date-time", example = "2023-10-01T00:00:00")
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
        UDR udr = udrService.getUDRForSubscriber(msisdn, start, end);
        return ResponseEntity.ok(udr);
    }


    @Operation(summary = "Получить UDR запись для всех абонентов",
               description = "Возвращает UDR запись для всех абонентов за запрашиваемый период.",
               tags = {"UDR"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно получена UDR запись"),
        @ApiResponse(responseCode = "400", description = "Неверные параметры запроса"),
    })   
    @GetMapping("/all")
    public ResponseEntity<List<UDR>> getUDRsForAllSubscribers(
            @Parameter(description = "Дата начала периода (формат: yyyy-MM-dd'T'HH:mm:ss)", required = false)
            @Schema(format = "date-time", example = "2023-10-01T00:00:00")
            @RequestParam(required = false) String startDate,
            @Parameter(description = "Дата окончания периода (формат: yyyy-MM-dd'T'HH:mm:ss)", required = false)
            @Schema(format = "date-time", example = "2023-10-01T00:00:00")
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
        List<UDR> udrs = udrService.getUDRsForAllSubscribers(start, end);
        return ResponseEntity.ok(udrs);
    }
}