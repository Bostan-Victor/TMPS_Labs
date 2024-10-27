package domain.singleton;

import domain.models.order.Order;

public class OrderProcessor {
    private static OrderProcessor instance;

    private OrderProcessor() {}

    public static OrderProcessor getInstance() {
        if (instance == null) {
            instance = new OrderProcessor();
        }
        return instance;
    }

    public void process(Order order) {
        order.processOrder();
    }
}
