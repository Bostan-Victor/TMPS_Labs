package domain.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        return true;
    }
}