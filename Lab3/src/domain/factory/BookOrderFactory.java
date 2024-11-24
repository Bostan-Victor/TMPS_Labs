package domain.factory;

import domain.models.order.BookOrder;
import domain.models.order.Order;

public class BookOrderFactory extends AbstractOrderFactory {
    @Override
    public Order createOrder() {
        return new BookOrder();
    }
}
