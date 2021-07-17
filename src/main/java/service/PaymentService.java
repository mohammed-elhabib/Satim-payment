package service;

import domain.Order;
import domain.Payment;
import lombok.Data;

@Data
public class PaymentService {
    ISatimService satimService;
    IEmailService emailService;
    public String createPayment(Order order) throws Exception {
      try {
          Payment payment=  satimService.createPayment("Account vendor ID",order.getTotalAmount());
          //TODO:Save Payment Information

          return payment.getDirectURl();
      } catch (Exception e){
          throw new Exception("can't create Payment ");
      }
    }
   public boolean confirmPayment(String paymentId,String payerId) throws Exception {
       try {
           Payment payment=  satimService.executePayment(paymentId,payerId);
           //TODO:Update Payment Information

           emailService.sendEmail("clientId");
           return true;

       }catch (Exception e){
           throw new Exception("can't confirm Payment ");
       }
   }
}
