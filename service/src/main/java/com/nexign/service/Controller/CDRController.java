package com.nexign.service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.service.Service.CdrGeneratorService;

@RestController
@RequestMapping("/api/v1/cdr")
public class CDRController {

@Autowired

    private CdrGeneratorService cdrGeneratorService;

    @PostMapping("/generate")
    public String generateCDR() {
        cdrGeneratorService.generateCDR(1000);
        return "CDR созданы";
    }

}
