package domain.singleton;

import domain.models.order.Order;
import domain.observer.Observer;
import domain.observer.OrderStatusNotifier;

public class OrderProcessor {
    private static OrderProcessor instance;
    private OrderStatusNotifier notifier;

    private OrderProcessor() {
        notifier = new OrderStatusNotifier();
    }

    public static OrderProcessor getInstance() {
        if (instance == null) {
            instance = new OrderProcessor();
        }
        return instance;
    }

    public void addObserver(Observer observer) {
        notifier.addObserver(observer);
    }

    public void process(Order order) {
        order.processOrder();
        notifier.notifyObservers("Order processed: " + order.getTitle() + ", Quantity: " + order.getQuantity());
    }
}
