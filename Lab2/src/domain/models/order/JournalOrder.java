package domain.models.order;

public class JournalOrder extends Order {
    @Override
    public void processOrder() {
        System.out.println("Processing journal order for: " + getTitle() + ", Quantity: " + getQuantity());
    }
}
