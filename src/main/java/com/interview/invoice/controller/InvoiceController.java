package com.interview.invoice.controller;

import com.interview.invoice.dto.InvoiceRequest;
import com.interview.invoice.dto.InvoiceResponse;

import com.interview.invoice.dto.OverdueRequest;
import com.interview.invoice.model.Invoice;
import com.interview.invoice.model.InvoiceStatus;
import com.interview.invoice.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest request) {
        System.out.println(request);
        Invoice invoice = invoiceService.createInvoice(request.getAmount(), LocalDate.parse(request.getDueDate()));
        return ResponseEntity.status(201).body(new InvoiceResponse(invoice.getId()));
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @PostMapping("/process-overdue")
    public ResponseEntity<?> processOverdueInvoices(@RequestBody OverdueRequest request) {
        List<Invoice> overdueInvoices = invoiceService.getOverdueInvoices();
        overdueInvoices.forEach(invoice -> {
            if (invoice.getPaidAmount() > 0 && invoice.getPaidAmount() < invoice.getAmount()) {
                double remainingAmount = invoice.getAmount() - invoice.getPaidAmount();
                double newAmount = remainingAmount + request.getLateFee();
                invoice.setStatus(InvoiceStatus.PAID);
                invoiceService.createInvoice(newAmount, LocalDate.now().plusDays(request.getOverDueDays()));
            } else if (invoice.getPaidAmount() == 0) {
                double newAmount = invoice.getAmount() + request.getLateFee();
                invoice.setStatus(InvoiceStatus.VOID);
                invoiceService.createInvoice(newAmount, LocalDate.now().plusDays(request.getOverDueDays()));
            }
        });
        return ResponseEntity.ok("Overdue invoices processed");
    }
}

