package com.luxjobstats.service;

import com.luxjobstats.dto.EmployeesBySectorDTO;
import com.luxjobstats.dto.TotalEmployeesByYearDTO;
import com.luxjobstats.dto.TrendPointDTO;
import com.luxjobstats.repository.FactSalariesByCharacteristicsRepository;
import com.luxjobstats.repository.FactSalariesByNationalityRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // overview stats based on nationality fact 
    public Object getOverview() {
        
        // pulling totals per year
        List<Object[]> rows = factSalariesByNationalityRepository.getTotalEmployeesByYear();
        List<TotalEmployeesByYearDTO> totals = new ArrayList<>();

        for (Object[] row : rows) {
            Integer year = ((Integer) row[0]).intValue();
            Long emp = (Long) row[1];
            totals.add(new TotalEmployeesByYearDTO(year, emp));
        }

        TotalEmployeesByYearDTO latest = totals.size() > 0 ? totals.get(totals.size() - 1) : null;

        // pulling global trend
        List<Object[]> trRows = factSalariesByNationalityRepository.getTrendOverTime();
        List<TrendPointDTO> trend = new ArrayList<>();

        for (Object[] row : trRows) {
            LocalDate d = (LocalDate) row[0];
            Long emp = (Long) row[1];
            trend.add(new TrendPointDTO(d, emp));
        }

        Map<String, Object> res = new HashMap<>();
        res.put("latest", latest);
        res.put("years", totals);
        res.put("trend", trend);

        return res;
    }

    // sector based analytics
    public Object getSectorStatistics(Long sectorId) {

        // trend for this sector
        List<Object[]> trRows = factSalariesByNationalityRepository.getSectorTrend(sectorId);
        List<TrendPointDTO> trend = new ArrayList<>();

        for (Object[] row : trRows) {
            LocalDate d = (LocalDate) row[0];
            Long emp = (Long) row[1];
            trend.add(new TrendPointDTO(d, emp));
        }

        // totals per year for the sector
        List<Object[]> yRows = factSalariesByNationalityRepository.getSectorEmployeesByYear(sectorId);
        List<TotalEmployeesByYearDTO> perYear = new ArrayList<>();

        for (Object[] row : yRows) {
            Integer year = ((Integer) row[0]).intValue();
            Long emp = (Long) row[1];
            perYear.add(new TotalEmployeesByYearDTO(year, emp));
        }

        Map<String, Object> res = new HashMap<>();
        res.put("sectorId", sectorId);
        res.put("trend", trend);
        res.put("perYear", perYear);

        return res;
    }

    // nationality based analytics
    public Object getNationalityStatistics(Long nationalityId) {

        // employees grouped by sctor
        List<Object[]> secRows = factSalariesByNationalityRepository.getEmployeesBySectorForNationality(nationalityId);
        List<EmployeesBySectorDTO> perSector = new ArrayList<>();

        for (Object[] row : secRows) {
            String name = (String) row[0];
            Long emp = (Long) row[1];
            perSector.add(new EmployeesBySectorDTO(name, emp));
        }

        // trend for nat.
        List<Object[]> trRows = factSalariesByNationalityRepository.getNationalityTrend(nationalityId);
        List<TrendPointDTO> trend = new ArrayList<>();

        for (Object[] row : trRows) {
            LocalDate d = (LocalDate) row[0];
            Long emp = (Long) row[1];
            trend.add(new TrendPointDTO(d, emp));
        }

        Map<String, Object> res = new HashMap<>();
        res.put("nationalityId", nationalityId);
        res.put("perSector", perSector);
        res.put("trend", trend);

        return res;
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

    public List<EmployeesBySectorDTO> getEmployeesBySectorForNationality(Long nationalityId) {
        List<Object[]> rows =
                factSalariesByNationalityRepository.getEmployeesBySectorForNationality(nationalityId);

        List<EmployeesBySectorDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            String sectorName = (String) row[0];
            Long employees = (Long) row[1];
            result.add(new EmployeesBySectorDTO(sectorName, employees));
        }

        return result;
    }

    //* /trend
    public List<TrendPointDTO> getTrendOverTime() {

        var rows = factSalariesByNationalityRepository.getTrendOverTime();
        var trend = new ArrayList<TrendPointDTO>();

        for (Object[] row : rows) {
            LocalDate d = (LocalDate) row[0];
            Long emp = (Long) row[1];
            trend.add(new TrendPointDTO(d, emp));
        }

        return trend;
    }





}
