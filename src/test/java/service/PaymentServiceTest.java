package service;

import Validators.PaymentValidator;
import domain.Order;
import domain.Payment;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {
    @Mock
    ISatimService satimService;
    @Mock
    IEmailService emailService;
    @Mock
    Order order;

    PaymentService paymentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        paymentService = new PaymentService();
        paymentService.setEmailService(emailService);
        paymentService.setSatimService(satimService);
        when(order.getTotalAmount()).thenReturn(250d);
    }

    @SneakyThrows
    @Test
    public void createPaymentTestAccountNotValid() {
        var vendorAccount = "";
        when(satimService.createPayment("", 250d)).thenThrow(new Exception("vendor Account not valid"));
        assertThrows(Exception.class, () -> paymentService.createPayment(order));
    }
    @SneakyThrows
    @Test
    public void createPaymentTestTotalEqZero() {
        var vendorAccount = "A874874DDFD";
        when(satimService.createPayment("A874874DDFD", 0)).thenThrow(new Exception("Total amount must be  not eq 0"));
        assertThrows(Exception.class, () -> paymentService.createPayment(order));
    }
    @SneakyThrows
    @Test
    public void createPaymentValid() {
        when(satimService.createPayment("Account vendor ID", 250d)).thenReturn(Payment.builder().directURl("localhost:8080/endPayment").build());
        assertEquals(paymentService.createPayment(order), "localhost:8080/endPayment");
    }
    @SneakyThrows
    @Test
    public void confirmPaymentPaymentIDNotValid(){
        when(satimService.executePayment("A874874DDFD", "5454545")).thenThrow(new Exception("Payment Id not valid"));
        assertThrows(Exception.class, () -> paymentService.confirmPayment("A874874DDFD", "5454545"));
        verify(emailService, times(0)).sendEmail("clientId");
    }
    @SneakyThrows
    @Test
    public void confirmPaymentPayerIDNotValid(){
        when(satimService.executePayment("A874874DDFD", "5454545")).thenThrow(new Exception("payer Id not valid"));
        assertThrows(Exception.class, () -> paymentService.confirmPayment("A874874DDFD", "5454545"));
        verify(emailService, times(0)).sendEmail("clientId");
    }
    @SneakyThrows
    @Test
    public void confirmPaymentValid(){
        when(satimService.executePayment("A874874DDFD", "5454545")).thenReturn(Payment.builder().build());
        assertEquals( paymentService.confirmPayment("A874874DDFD", "5454545"),true);
        verify(emailService, times(1)).sendEmail("clientId");
    }
}
