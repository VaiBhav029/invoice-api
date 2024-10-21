package com.interview.invoice.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Invoice {
        private Long id;
        private double amount;
        private double paidAmount = 0;
        private LocalDate dueDate;
        private InvoiceStatus status = InvoiceStatus.PENDING;

        public boolean isPaid() {
            return paidAmount >= amount;
        }
}
