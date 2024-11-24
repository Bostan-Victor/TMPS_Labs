package domain.facade;

import domain.models.order.Order;
import domain.singleton.OrderProcessor;
import domain.strategy.PaymentStrategy;

public class OrderServiceFacade {
    private final OrderProcessor orderProcessor;

    public OrderServiceFacade(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    public void processOrderWithPayment(Order order, double amount, PaymentStrategy paymentStrategy) {
        orderProcessor.process(order);

        boolean paymentSuccessful = paymentStrategy.pay(amount);

        if (paymentSuccessful) {
            System.out.println("Order processed and payment successful.");
        } else {
            System.out.println("Payment failed.");
        }
    }

    // Method for handling orders with external payment services through the Adapter pattern
    public void processOrderWithExternalPayment(Order order, double amount, PaymentStrategy paymentAdapter) {
        orderProcessor.process(order);

        boolean paymentSuccessful = paymentAdapter.pay(amount);

        if (paymentSuccessful) {
            System.out.println("Order processed and payment successful through external service.");
        } else {
            System.out.println("Payment failed through external service.");
        }
    }
}
