package com.luxjobstats.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luxjobstats.model.ResidenceNationality;
import com.luxjobstats.service.ResidenceService;

@RestController
@RequestMapping("/api/residents")
public class ResidenceController {

    private final ResidenceService residenceService;

    public ResidenceController(ResidenceService residenceService){
        this.residenceService = residenceService;
    }

    @GetMapping
    public List<ResidenceNationality> getAll(){
        return residenceService.getAllResidentNationalities();
    }
    
}
