package domain.factory;

import domain.models.order.JournalOrder;
import domain.models.order.Order;

public class JournalOrderFactory extends AbstractOrderFactory {
    @Override
    public Order createOrder() {
        return new JournalOrder();
    }
}
