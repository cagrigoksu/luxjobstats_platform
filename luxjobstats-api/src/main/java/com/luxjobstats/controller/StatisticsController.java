package com.luxjobstats.controller;

import com.luxjobstats.dto.TotalEmployeesByYearDTO;
import com.luxjobstats.service.StatisticsService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/overview")
    public Object getOverview() {
        // TODO: replace Object with a proper DTO later
        return statisticsService.getOverview();
    }

    @GetMapping("/sector/{sectorId}")
    public Object getSectorStatistics(@PathVariable Long sectorId) {
        return statisticsService.getSectorStatistics(sectorId);
    }

    @GetMapping("/nationality/{nationalityId}")
    public Object getNationalityStatistics(@PathVariable Long nationalityId) {
        return statisticsService.getNationalityStatistics(nationalityId);
    }

    @GetMapping("/employees-by-year")
    public List<TotalEmployeesByYearDTO> getEmployeesByYear() {
        return statisticsService.getTotalEmployeesByYear();
    }

}
