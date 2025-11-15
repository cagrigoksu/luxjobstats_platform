package com.luxjobstats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luxjobstats.model.Sector;
import com.luxjobstats.repository.SectorRepository;

@Service
public class SectorService {

    private final SectorRepository sectorRepository;

    public SectorService(SectorRepository sectorRepository){
        this.sectorRepository = sectorRepository;
    }

    public List<Sector> getAllSectors(){
        return sectorRepository.findAll();
    }
    
}
