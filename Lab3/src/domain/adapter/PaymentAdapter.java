package domain.adapter;

import domain.external.ExternalPaymentService;
import domain.strategy.PaymentStrategy;

public class PaymentAdapter implements PaymentStrategy {
    private final ExternalPaymentService paymentService;

    public PaymentAdapter(ExternalPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public boolean pay(double amount) {
        return paymentService.processPayment(amount);
    }
}
