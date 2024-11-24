package domain.models.order;

public class MagazineOrder extends Order {
    @Override
    public void processOrder() {
        System.out.println("Processing magazine order for: " + getTitle() + ", Quantity: " + getQuantity());
    }
}
