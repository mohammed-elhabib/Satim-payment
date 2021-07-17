package controller;

import Validators.PaymentValidator;
import domain.Order;
import domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.PaymentService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentController {


    PaymentService paymentService;
    public String createPayment(Order order) throws Exception {
       if(PaymentValidator.orderValidate(order)){
        return    paymentService.createPayment(order);
       }
       throw new Exception("Create Payment fail");
    }
    public boolean confirmPayment(String paymentId,String payerId) throws Exception {
        if( PaymentValidator.paymentIdValidate(paymentId)&& PaymentValidator.payerIdValidate(payerId)){
            return    paymentService.confirmPayment(paymentId,payerId);
        }
        throw new Exception("confirmPayment fail");
    }
}
