package com.interview.invoice.model;

import lombok.Data;

@Data
public class Payment {

    private Long id;
    private double amount;
    private Long invoiceId;
}
