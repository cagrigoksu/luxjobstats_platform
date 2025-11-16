package com.luxjobstats.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luxjobstats.model.Gender;
import com.luxjobstats.service.GenderService;

@RestController
@RequestMapping("/api/genders")
public class GenderController {
        private final GenderService genderService;

    public GenderController(GenderService genderService){
        this.genderService = genderService;
    }

    @GetMapping
    public List<Gender> getAll(){
        return genderService.getAllGenders();
    }
    
}
