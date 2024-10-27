package domain.models.order;

public class BookOrder extends Order {
    @Override
    public void processOrder() {
        System.out.println("Processing book order for: " + getTitle() + ", Quantity: " + getQuantity());
    }
}
