package validators;

import Validators.PaymentValidator;
import domain.Order;
import domain.OrderItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PaymentValidatorTest {

    @Test
    public void orderValidateItemsIsNullOrEmpty() {
        Order orderItemsNull = Order.builder().items(null).clientName("client test").TotalAmount(0).build();
        assertThrows(Exception.class, () -> PaymentValidator.orderValidate(orderItemsNull));


        Order orderItemsIsEmpty = Order.builder().items(new ArrayList<>()).clientName("client test").TotalAmount(0).build();
        assertThrows(Exception.class, () -> PaymentValidator.orderValidate(orderItemsIsEmpty));

    }

    @Test

    public void orderValidateTotalAmountNotValid() {
        Order orderTotalAmountNotValid = Order.builder().TotalAmount(20)
                .items(List.of(
                        OrderItem.builder().Name("item 1").price(5).build(),
                        OrderItem.builder().Name("item 2").price(12).build())).clientName("client Test").build();
        assertThrows(Exception.class, () -> PaymentValidator.orderValidate(orderTotalAmountNotValid));
    }

    @Test

    public void orderValidateOrderValid() throws Exception {
        Order orderTotalAmountIsValid = Order.builder().TotalAmount(20)
                .items(List.of(
                        OrderItem.builder().Name("item 1").price(5).build(),
                        OrderItem.builder().Name("item 2").price(15).build())).clientName("client Test").build();
        assertEquals(PaymentValidator.orderValidate(orderTotalAmountIsValid), true);
    }

    @Test

    public void payerIdValidatePayerIsEmpty() {
        String payerId = "";
        assertEquals(PaymentValidator.payerIdValidate(payerId), false);

    }

    @Test

    public void paymentIdValidatePaymentIsEmpty() {
        String paymentId = "";
        assertEquals(PaymentValidator.paymentIdValidate(paymentId), false);
    }

    @Test

    public void payerIdValidatePayerIsValid() {
        String payerId = "000A5852CCR81";
        assertEquals(PaymentValidator.payerIdValidate(payerId), true);
    }

    @Test

    public void paymentIdValidatePaymentIsValid() {
        String paymentId = "23121@ddd54d";
        assertEquals(PaymentValidator.paymentIdValidate(paymentId), true);
    }

}
