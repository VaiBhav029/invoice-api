package com.interview.invoice.controller;

import com.interview.invoice.dto.PaymentRequest;
import com.interview.invoice.model.Payment;
import com.interview.invoice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices/{invoiceId}/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> payInvoice(@PathVariable Long invoiceId, @RequestBody PaymentRequest request) {
        Payment payment = paymentService.payInvoice(invoiceId, request.getAmount());
        return ResponseEntity.status(201).body(payment);
    }
}



