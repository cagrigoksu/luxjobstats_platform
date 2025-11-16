package com.luxjobstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxjobstats.model.Gender;

public interface GenderRepository extends JpaRepository<Gender, Long>{

    
}
