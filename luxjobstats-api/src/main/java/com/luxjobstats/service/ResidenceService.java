package com.luxjobstats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luxjobstats.model.ResidenceNationality;
import com.luxjobstats.repository.ResidenceRepository;

@Service
public class ResidenceService {

    private final ResidenceRepository residenceRepository;

    public ResidenceService(ResidenceRepository residenceRepository){
        this.residenceRepository = residenceRepository;
    }

    public List<ResidenceNationality> getAllResidentNationalities(){
        return residenceRepository.findAll();
    }
    
}
