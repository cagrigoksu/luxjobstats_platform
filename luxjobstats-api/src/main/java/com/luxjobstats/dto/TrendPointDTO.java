package com.luxjobstats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TrendPointDTO {

    private LocalDate date;
    private Long employees;
    
}
