package domain.decorator;

import domain.models.order.Order;

public abstract class OrderDecorator extends Order {
    protected Order decoratedOrder;

    public OrderDecorator(Order order) {
        this.decoratedOrder = order;
    }

    public abstract void processOrder();
}
