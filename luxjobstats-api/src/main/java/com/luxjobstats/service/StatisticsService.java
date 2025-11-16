package com.luxjobstats.service;

import com.luxjobstats.dto.TotalEmployeesByYearDTO;
import com.luxjobstats.repository.FactSalariesByCharacteristicsRepository;
import com.luxjobstats.repository.FactSalariesByNationalityRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final FactSalariesByNationalityRepository factSalariesByNationalityRepository;
    private final FactSalariesByCharacteristicsRepository factSalariesByCharacteristicsRepository;

    public StatisticsService(FactSalariesByNationalityRepository factSalariesByNationalityRepository,
                             FactSalariesByCharacteristicsRepository factSalariesByCharacteristicsRepository) {
        this.factSalariesByNationalityRepository = factSalariesByNationalityRepository;
        this.factSalariesByCharacteristicsRepository = factSalariesByCharacteristicsRepository;
    }

    public Object getOverview() {
        // TODO: implement real overview stats from both fact tables
        return "not implemented yet";
    }

    public Object getSectorStatistics(Long sectorId) {
        // TODO: implement sectorbased stats
        return "not implemented yet";
    }

    public Object getNationalityStatistics(Long nationalityId) {
        // TODO: implement nationality-based stats
        return "not implemented yet";
    }

    public List<TotalEmployeesByYearDTO> getTotalEmployeesByYear() {

        List<Object[]> rows = factSalariesByNationalityRepository.getTotalEmployeesByYear();
        List<TotalEmployeesByYearDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            Number yearNumber = (Number) row[0];
            Number employeesNumber = (Number) row[1];

            Integer year = yearNumber.intValue();
            Long employees = employeesNumber.longValue();

            result.add(new TotalEmployeesByYearDTO(year, employees));
        }

        return result;
    }



}
