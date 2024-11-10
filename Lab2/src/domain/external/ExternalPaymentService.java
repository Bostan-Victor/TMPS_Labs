package domain.external;

public class ExternalPaymentService {
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " through external service.");
        return true; // Simulate payment success
    }
}
