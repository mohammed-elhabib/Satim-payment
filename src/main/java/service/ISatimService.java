package service;

import domain.Payment;

public interface ISatimService {
    Payment createPayment(String account_vendor_id, double totalAmount) throws Exception;

    Payment executePayment(String paymentId, String payerId) throws  Exception;
}
