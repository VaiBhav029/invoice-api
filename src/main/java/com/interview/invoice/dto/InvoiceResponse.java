package com.interview.invoice.dto;

import lombok.Data;

@Data
public class InvoiceResponse {

    private Long id;

    public InvoiceResponse(Long id) {
        this.id = id;
    }

}
