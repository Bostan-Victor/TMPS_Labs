package domain.models.builder;

import domain.models.order.Order;

public class OrderBuilder {
    private Order order;

    public OrderBuilder setOrderType(Order order) {
        this.order = order;
        return this;
    }

    public OrderBuilder setOrderDetails(String title, int quantity) {
        this.order.setTitle(title);
        this.order.setQuantity(quantity);
        return this;
    }

    public Order build() {
        return order;
    }
}
