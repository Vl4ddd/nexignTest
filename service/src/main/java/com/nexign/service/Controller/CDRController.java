package com.nexign.service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.service.Service.CdrGeneratorService;

import io.swagger.v3.oas.annotations.Operation;
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

}
