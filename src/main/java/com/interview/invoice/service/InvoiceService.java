package com.interview.invoice.service;

import com.interview.invoice.model.Invoice;
import com.interview.invoice.model.InvoiceStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InvoiceService {

        private Map<Long, Invoice> invoiceStore = new HashMap<>();
        private AtomicLong idGenerator = new AtomicLong(1);

        // Create a new invoice
        public Invoice createInvoice(double amount, LocalDate dueDate) {
            Invoice invoice = new Invoice();
            invoice.setId(idGenerator.getAndIncrement());
            invoice.setAmount(amount);
            invoice.setDueDate(dueDate);
            invoiceStore.put(invoice.getId(), invoice);
            return invoice;
        }

        // Get all invoices
        public List<Invoice> getAllInvoices() {
            return new ArrayList<>(invoiceStore.values());
        }

        // Get invoice by ID
        public Invoice getInvoiceById(Long id) {
            return invoiceStore.get(id);
        }

        // Update an invoice
        public Invoice updateInvoice(Invoice invoice) {
            invoiceStore.put(invoice.getId(), invoice);
            return invoice;
        }

        // Get all overdue invoices
        public List<Invoice> getOverdueInvoices() {
            List<Invoice> overdueInvoices = new ArrayList<>();
            for (Invoice invoice : invoiceStore.values()) {
                if (invoice.getStatus() == InvoiceStatus.PENDING && invoice.getDueDate().isBefore(LocalDate.now())) {
                    overdueInvoices.add(invoice);
                }
            }
            return overdueInvoices;
    }
}
