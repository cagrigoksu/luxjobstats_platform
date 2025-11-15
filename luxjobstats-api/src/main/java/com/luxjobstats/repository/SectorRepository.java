package com.luxjobstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxjobstats.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    
}
