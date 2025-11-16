package com.luxjobstats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luxjobstats.model.Gender;
import com.luxjobstats.repository.GenderRepository;

@Service
public class GenderService {
    private final GenderRepository GenderRepository;

    public GenderService(GenderRepository GenderRepository){
        this.GenderRepository = GenderRepository;
    }

    public List<Gender> getAllGenders(){
        return GenderRepository.findAll();
    }
    
}
