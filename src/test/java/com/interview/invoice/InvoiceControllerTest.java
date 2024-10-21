package com.interview.invoice;

import com.interview.invoice.controller.InvoiceController;
import com.interview.invoice.model.Invoice;
import com.interview.invoice.model.InvoiceStatus;
import com.interview.invoice.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;


    //Test for creating an invoice with given example
    @Test
    public void testCreateInvoice() throws Exception {
        // Mock the service
        Invoice mockInvoice = new Invoice();
        mockInvoice.setId(1L);
        mockInvoice.setAmount(199.99);
        mockInvoice.setDueDate(LocalDate.of(2021, 9, 11));
        Mockito.when(invoiceService.createInvoice(199.99, LocalDate.of(2021, 9, 11)))
                .thenReturn(mockInvoice);

        // Perform the POST request
        mockMvc.perform(post("/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"amount\": 199.99, \"dueDate\": \"2021-09-11\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }
    //Test case to get all the invoices
    @Test
    public void testGetAllInvoices() throws Exception {
        // Mock the service
        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setAmount(199.99);
        invoice1.setPaidAmount(0);
        invoice1.setDueDate(LocalDate.of(2021, 9, 11));
        invoice1.setStatus(InvoiceStatus.PENDING);

        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setAmount(99.99);
        invoice2.setPaidAmount(99.99);
        invoice2.setDueDate(LocalDate.of(2021, 8, 15));
        invoice2.setStatus(InvoiceStatus.PAID);

        Mockito.when(invoiceService.getAllInvoices()).thenReturn(Arrays.asList(invoice1, invoice2));

        // Perform the GET request
        mockMvc.perform(get("/invoices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value(199.99))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].status").value("PAID"));

    }
    //Test for overdue invoices
    @Test
    public void testProcessOverdueInvoices() throws Exception {
        // Mock the service
        Invoice overdueInvoice = new Invoice();
        overdueInvoice.setId(1L);
        overdueInvoice.setAmount(100.0);
        overdueInvoice.setPaidAmount(0);
        overdueInvoice.setDueDate(LocalDate.of(2021, 9, 11));
        overdueInvoice.setStatus(InvoiceStatus.PENDING);

        Mockito.when(invoiceService.getOverdueInvoices()).thenReturn(Arrays.asList(overdueInvoice));

        // Perform the POST request
        mockMvc.perform(post("/invoices/process-overdue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lateFee\": 10.5, \"overdueDays\": 10 }"))
                .andExpect(status().isOk());
    }


}

