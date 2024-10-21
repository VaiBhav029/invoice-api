package com.interview.invoice.dto;

import lombok.Data;

@Data
public class InvoiceRequest {
    private double amount;
    private String dueDate;
}
