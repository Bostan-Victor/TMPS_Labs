package domain.decorator;

import domain.models.order.Order;

public class GiftWrappedOrderDecorator extends OrderDecorator {
    public GiftWrappedOrderDecorator(Order order) {
        super(order);
    }

    @Override
    public void processOrder() {
        decoratedOrder.processOrder();
        System.out.println("Order is gift-wrapped.");
    }
}
