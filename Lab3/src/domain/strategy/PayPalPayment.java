package domain.strategy;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        return true;
    }
}