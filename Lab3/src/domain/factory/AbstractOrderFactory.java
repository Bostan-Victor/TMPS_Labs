package domain.factory;

import domain.models.order.Order;

public abstract class AbstractOrderFactory {
    public abstract Order createOrder();
}
