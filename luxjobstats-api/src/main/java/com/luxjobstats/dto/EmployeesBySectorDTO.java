package com.luxjobstats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeesBySectorDTO {

    private String sector;
    private Long employees;
}
