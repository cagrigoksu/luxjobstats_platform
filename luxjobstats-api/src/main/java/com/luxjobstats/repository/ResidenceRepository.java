package com.luxjobstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxjobstats.model.ResidenceNationality;

public interface ResidenceRepository extends JpaRepository<ResidenceNationality, Long>{
    
}
