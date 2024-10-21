package com.interview.invoice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OverdueRequest {
    private double lateFee;
    private long overDueDays;
}
