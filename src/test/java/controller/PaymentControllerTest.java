package controller;

import Validators.PaymentValidator;
import domain.Order;
import domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import service.PaymentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    @Mock
    PaymentService paymentService;
    PaymentController paymentController;
    @Mock
    Order order;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        paymentController = new PaymentController();
        paymentController.setPaymentService(paymentService);

    }
    @Test
    public void createPaymentOrderWithoutItems()  {
       when(order.getItems()).thenReturn(null);
        assertThrows(Exception.class, () -> paymentController.createPayment(order));
    }
    @Test
    public void createPaymentOrderTotalNotEqItemsSum()  {
        when(order.getItems()).thenReturn(List.of(OrderItem.builder().price(25).build(),OrderItem.builder().price(15).build()));
        when(order.getTotalAmount()).thenReturn(50d);
        assertThrows(Exception.class, () -> paymentController.createPayment(order));
    }
    @Test
    public void createPaymentWithExceptionService() throws Exception {
        when(order.getItems()).thenReturn(List.of(OrderItem.builder().price(25).build(),OrderItem.builder().price(25).build()));
        when(order.getTotalAmount()).thenReturn(50d);
        when(paymentService.createPayment(order)).thenThrow(new Exception("not found Satim api"));
        assertThrows(Exception.class, () -> paymentController.createPayment(order));
    }
    @Test
    public void createPaymentValid() throws Exception {
        when(order.getItems()).thenReturn(List.of(OrderItem.builder().price(25).build(),OrderItem.builder().price(25).build()));
        when(order.getTotalAmount()).thenReturn(50d);
        when(paymentService.createPayment(order)).thenReturn("localHost:8080");
        assertEquals(paymentController.createPayment(order),"localHost:8080");
    }
    @Test
    public void confirmPaymentWithPaymentIdNotValid()  {
        assertThrows(Exception.class, () -> paymentController.confirmPayment("","55454AAA55"));
    }
    @Test
    public void confirmPaymentWithPayerIdNotValid()  {

        assertThrows(Exception.class, () -> paymentController.confirmPayment("A87878787",""));
    }
    @Test
    public void confirmPaymentWithExceptionService() throws Exception {

        when(paymentService.confirmPayment("ACCC445456","AAA8554985498")).thenThrow(new Exception("not found Satim api"));
        assertThrows(Exception.class, () -> paymentController.confirmPayment("ACCC445456","AAA8554985498"));
    }
    @Test
    public void confirmPaymentValid() throws Exception {
        when(paymentService.confirmPayment("ACCC445456","AAA8554985498")).thenReturn(true);
        assertEquals(paymentController.confirmPayment("ACCC445456","AAA8554985498"),true);
    }

}
