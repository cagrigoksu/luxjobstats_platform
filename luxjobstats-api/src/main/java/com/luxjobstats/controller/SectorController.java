package com.luxjobstats.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luxjobstats.model.Sector;
import com.luxjobstats.service.SectorService;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService){
        this.sectorService = sectorService;
    }
    
    @GetMapping
    public List<Sector> getAll(){
        return sectorService.getAllSectors();
    }
}
