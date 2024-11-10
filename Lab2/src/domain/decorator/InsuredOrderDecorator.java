package domain.decorator;

import domain.models.order.Order;

public class InsuredOrderDecorator extends OrderDecorator {
    public InsuredOrderDecorator(Order order) {
        super(order);
    }

    @Override
    public void processOrder() {
        decoratedOrder.processOrder();
        System.out.println("Order is insured.");
    }
}
