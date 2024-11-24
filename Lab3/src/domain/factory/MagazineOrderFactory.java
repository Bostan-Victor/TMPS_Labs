package domain.factory;

import domain.models.order.MagazineOrder;
import domain.models.order.Order;

public class MagazineOrderFactory extends AbstractOrderFactory {
    @Override
    public Order createOrder() {
        return new MagazineOrder();
    }
}
