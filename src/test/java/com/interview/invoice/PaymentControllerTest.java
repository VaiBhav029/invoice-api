package com.interview.invoice;

import com.interview.invoice.controller.PaymentController;
import com.interview.invoice.model.Invoice;
import com.interview.invoice.model.InvoiceStatus;
import com.interview.invoice.model.Payment;
import com.interview.invoice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testPayInvoice() throws Exception {

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setInvoiceId(1234L);
        payment.setAmount(159.99);
        Mockito.when(paymentService.payInvoice(1234L, 159.99)).thenReturn(payment);

        // POST request
        mockMvc.perform(post("/invoices/1234/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"amount\": 159.99 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(159.99));
    }


}
