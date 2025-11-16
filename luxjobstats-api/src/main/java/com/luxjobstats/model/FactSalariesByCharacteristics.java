package com.luxjobstats.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "fact_salaries_by_characteristics")
@Getter
@Setter
public class FactSalariesByCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reference_date")
    private LocalDate referenceDate;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "residence_nationality_id")
    private ResidenceNationality residenceNationality;

    @ManyToOne
    @JoinColumn(name = "age_id")
    private Age age;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "employee_count")
    private Integer employeeCount;
}
