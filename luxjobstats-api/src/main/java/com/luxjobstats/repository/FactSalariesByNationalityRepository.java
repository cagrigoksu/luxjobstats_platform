package com.luxjobstats.repository;

import com.luxjobstats.model.FactSalariesByNationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FactSalariesByNationalityRepository extends JpaRepository<FactSalariesByNationality, Long> {

    @Query("""
        select extract(YEAR from f.referenceDate),
               sum(f.employeeCount)
        from FactSalariesByNationality f
        group by extract(YEAR from f.referenceDate)
        order by extract(YEAR from f.referenceDate)
    """)
    List<Object[]> getTotalEmployeesByYear();


    @Query("""
        select f.referenceDate, sum(f.employeeCount)
        from FactSalariesByNationality f
        group by f.referenceDate
        order by f.referenceDate
    """)
    List<Object[]> getTrendOverTime();



    @Query("""
        select f.referenceDate, sum(f.employeeCount)
        from FactSalariesByNationality f
        where f.sector.id = :sectorId
        group by f.referenceDate
        order by f.referenceDate
    """)
    List<Object[]> getSectorTrend(Long sectorId);


    @Query("""
        select extract(YEAR from f.referenceDate),
               sum(f.employeeCount)
        from FactSalariesByNationality f
        where f.sector.id = :sectorId
        group by extract(YEAR from f.referenceDate)
        order by extract(YEAR from f.referenceDate)
    """)
    List<Object[]> getSectorEmployeesByYear(Long sectorId);



    @Query("""
        select f.sector.sectorEn, sum(f.employeeCount)
        from FactSalariesByNationality f
        where f.nationality.id = :nationalityId
        group by f.sector.sectorEn
        order by sum(f.employeeCount) desc
    """)
    List<Object[]> getEmployeesBySectorForNationality(Long nationalityId);
    

    @Query("""
        select f.referenceDate, sum(f.employeeCount)
        from FactSalariesByNationality f
        where f.nationality.id = :nationalityId
        group by f.referenceDate
        order by f.referenceDate
    """)
    List<Object[]> getNationalityTrend(Long nationalityId);
}
