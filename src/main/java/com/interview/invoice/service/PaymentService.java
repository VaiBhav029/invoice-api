package com.interview.invoice.service;

import com.interview.invoice.model.Invoice;
import com.interview.invoice.model.InvoiceStatus;
import com.interview.invoice.model.Payment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PaymentService {
    private List<Payment> paymentStore = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);
    private final InvoiceService invoiceService;

    public PaymentService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Pay an invoice
    public Payment payInvoice(Long invoiceId, double amount) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        if (invoice == null) {
            throw new RuntimeException("Invoice not found");
        }

        // Create a new payment
        Payment payment = new Payment();
        payment.setId(idGenerator.getAndIncrement());
        payment.setInvoiceId(invoiceId);
        payment.setAmount(amount);
        paymentStore.add(payment);

        // Update the invoice
        invoice.setPaidAmount(invoice.getPaidAmount() + amount);
        if (invoice.isPaid()) {
            invoice.setStatus(InvoiceStatus.PAID);
        }
        invoiceService.updateInvoice(invoice);

        return payment;
    }
}