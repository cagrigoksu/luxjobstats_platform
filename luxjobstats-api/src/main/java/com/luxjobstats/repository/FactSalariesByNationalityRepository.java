package com.luxjobstats.repository;

import com.luxjobstats.model.FactSalariesByNationality;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FactSalariesByNationalityRepository extends JpaRepository<FactSalariesByNationality, Long> {

        @Query("""
        SELECT 
        EXTRACT(YEAR FROM f.referenceDate) AS year,
        SUM(f.employeeCount) AS employees
        FROM FactSalariesByNationality f
        GROUP BY EXTRACT(YEAR FROM f.referenceDate)
        ORDER BY year
        """)
        List<Object[]> getTotalEmployeesByYear();

        @Query("""
        SELECT f.sector.sectorEn, SUM(f.employeeCount)
        FROM FactSalariesByNationality f
        WHERE f.nationality.id = :nationalityId
        GROUP BY f.sector.sectorEn
        ORDER BY SUM(f.employeeCount) DESC
        """)
        List<Object[]> getEmployeesBySectorForNationality(Long nationalityId);


}
