package Validators;

import domain.Order;
import domain.OrderItem;

public class PaymentValidator {


    public static boolean orderValidate(Order order) throws Exception {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new Exception("must be not items empty or null");
        }
        double total = order.getItems().stream().mapToDouble(OrderItem::getPrice).sum();
        if (order.getTotalAmount() != total) {
            throw new Exception("must be sum price item = order total amount ");
        }
        return true;
    }

    public static boolean payerIdValidate(String payerId) {

        return !payerId.isEmpty();
    }

    public static boolean paymentIdValidate(String paymentId) {
        return !paymentId.isEmpty();
    }
}
