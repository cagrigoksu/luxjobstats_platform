package com.luxjobstats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalEmployeesByYearDTO {

    private Integer year;

    private Long employeeNumber;
    
}
