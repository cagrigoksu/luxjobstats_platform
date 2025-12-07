package com.luxjobstats.repository;

import com.luxjobstats.model.FactDataByCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FactDataByCharacteristicsRepository extends JpaRepository<FactDataByCharacteristics, Long> {

    // gender over time 
    @Query("""
        select f.referenceDate, f.gender.genderEn, sum(f.numberOfEmployee)
        from FactDataByCharacteristics f
        group by f.referenceDate, f.gender.genderEn
        order by f.referenceDate, f.gender.genderEn
    """)
    List<Object[]> getEmployeesByGenderOverTime();

    // per year breakdowns
    @Query(value = """
        select g.gender_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_gender g on g.id = f.gender_id
        where extract(year from f.reference_date) = :year
        group by g.gender_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByGenderForYear(int year);

    // all years breakdowns
    @Query(value = """
        select g.gender_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_gender g on g.id = f.gender_id
        group by g.gender_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByGenderAllYears();

    @Query(value = """
        select a.age_label_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_age a on a.id = f.age_id
        where extract(year from f.reference_date) = :year
        group by a.age_label_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByAgeForYear(int year);

    @Query(value = """
        select s.status_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_status s on s.id = f.status_id
        where extract(year from f.reference_date) = :year
        group by s.status_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByStatusForYear(int year);

    @Query(value = """
        select r.residence_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_residence_on_characteristics r on r.id = f.residence_id
        where extract(year from f.reference_date) = :year
        group by r.residence_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByResidenceCharForYear(int year);

    @Query(value = """
        select a.age_label_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_age a on a.id = f.age_id
        group by a.age_label_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByAgeAllYears();

    @Query(value = """
        select s.status_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_status s on s.id = f.status_id
        group by s.status_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByStatusAllYears();

    @Query(value = """
        select r.residence_en as label, sum(f.number_of_employee) as cnt
        from fact_data_by_characteristics f
        join dim_residence_on_characteristics r on r.id = f.residence_id
        group by r.residence_en
        order by cnt desc
    """, nativeQuery = true)
    List<Object[]> getByResidenceCharAllYears();
}
