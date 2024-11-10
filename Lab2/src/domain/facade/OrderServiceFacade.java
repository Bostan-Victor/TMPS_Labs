package domain.facade;

import domain.adapter.PaymentAdapter;
import domain.models.order.Order;
import domain.singleton.OrderProcessor;

public class OrderServiceFacade {
    private final OrderProcessor orderProcessor;
    private final PaymentAdapter paymentAdapter;

    public OrderServiceFacade(OrderProcessor orderProcessor, PaymentAdapter paymentAdapter) {
        this.orderProcessor = orderProcessor;
        this.paymentAdapter = paymentAdapter;
    }

    public void processOrderWithPayment(Order order, double amount) {
        orderProcessor.process(order);
        boolean paymentSuccessful = paymentAdapter.pay(amount);
        if (paymentSuccessful) {
            System.out.println("Order processed and payment successful.");
        } else {
            System.out.println("Payment failed.");
        }
    }
}
