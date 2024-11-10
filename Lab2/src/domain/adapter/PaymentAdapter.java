package domain.adapter;

import domain.external.ExternalPaymentService;

public class PaymentAdapter {
    private final ExternalPaymentService paymentService;

    public PaymentAdapter(ExternalPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public boolean pay(double amount) {
        return paymentService.processPayment(amount);
    }
}
