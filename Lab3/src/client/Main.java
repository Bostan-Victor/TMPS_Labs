package client;

import domain.adapter.PaymentAdapter;
import domain.decorator.GiftWrappedOrderDecorator;
import domain.decorator.InsuredOrderDecorator;
import domain.external.ExternalPaymentService;
import domain.facade.OrderServiceFacade;
import domain.factory.BookOrderFactory;
import domain.factory.JournalOrderFactory;
import domain.factory.MagazineOrderFactory;
import domain.models.builder.OrderBuilder;
import domain.models.order.Order;
import domain.observer.CustomerNotifier;
import domain.observer.OrderLogger;
import domain.singleton.OrderProcessor;
import domain.strategy.CreditCardPayment;
import domain.strategy.PayPalPayment;
import domain.strategy.PaymentStrategy;

public class Main {
    public static void main(String[] args) {
        // Setup Observers
        OrderProcessor orderProcessor = OrderProcessor.getInstance();
        orderProcessor.addObserver(new OrderLogger());
        orderProcessor.addObserver(new CustomerNotifier());

        // Initialize External Payment Service and Adapter
        ExternalPaymentService paymentService = new ExternalPaymentService();
        PaymentAdapter paymentAdapter = new PaymentAdapter(paymentService);

        // Facade Setup
        OrderServiceFacade orderServiceFacade = new OrderServiceFacade(orderProcessor);

        // 1. Create Book Order with Gift Wrap and Insurance, and process with Credit Card Payment
        BookOrderFactory bookFactory = new BookOrderFactory();
        Order bookOrder = new OrderBuilder()
                .setOrderType(bookFactory.createOrder())
                .setOrderDetails("Java Programming Book", 10)
                .build();

        Order insuredBookOrder = new InsuredOrderDecorator(bookOrder);
        Order giftWrappedInsuredBookOrder = new GiftWrappedOrderDecorator(insuredBookOrder);

        // Process Book Order with Credit Card Payment
        PaymentStrategy creditCardPayment = new CreditCardPayment();
        orderServiceFacade.processOrderWithPayment(giftWrappedInsuredBookOrder, 100.0, creditCardPayment);

        System.out.println("\n---\n");

        // 2. Create Journal Order with Insurance, and process with PayPal Payment
        JournalOrderFactory journalFactory = new JournalOrderFactory();
        Order journalOrder = new OrderBuilder()
                .setOrderType(journalFactory.createOrder())
                .setOrderDetails("Science Journal", 5)
                .build();

        Order insuredJournalOrder = new InsuredOrderDecorator(journalOrder);

        // Process Journal Order with PayPal Payment
        PaymentStrategy paypalPayment = new PayPalPayment();
        orderServiceFacade.processOrderWithPayment(insuredJournalOrder, 50.0, paypalPayment);

        System.out.println("\n---\n");

        // 3. Create Magazine Order and process with External Payment Service
        MagazineOrderFactory magazineFactory = new MagazineOrderFactory();
        Order magazineOrder = new OrderBuilder()
                .setOrderType(magazineFactory.createOrder())
                .setOrderDetails("Tech Magazine", 3)
                .build();

        Order insuredMagazineOrder = new InsuredOrderDecorator(magazineOrder);

        // Process Magazine Order with External Payment Service via Adapter
        orderServiceFacade.processOrderWithExternalPayment(insuredMagazineOrder, 30.0, paymentAdapter);
    }
}

