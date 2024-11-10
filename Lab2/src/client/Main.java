package client;

import domain.adapter.PaymentAdapter;
import domain.decorator.GiftWrappedOrderDecorator;
import domain.decorator.InsuredOrderDecorator;
import domain.external.ExternalPaymentService;
import domain.facade.OrderServiceFacade;
import domain.factory.BookOrderFactory;
import domain.factory.JournalOrderFactory;
import domain.models.builder.OrderBuilder;
import domain.models.order.Order;
import domain.singleton.OrderProcessor;

public class Main {
    public static void main(String[] args) {
        // Original Order Creation
        BookOrderFactory bookFactory = new BookOrderFactory();
        Order bookOrder = new OrderBuilder()
                .setOrderType(bookFactory.createOrder())
                .setOrderDetails("Java Programming Book", 10)
                .build();

        // Applying Decorators
        Order insuredBookOrder = new InsuredOrderDecorator(bookOrder);
        Order giftWrappedInsuredBookOrder = new GiftWrappedOrderDecorator(insuredBookOrder);

        // Adapter Setup
        ExternalPaymentService paymentService = new ExternalPaymentService();
        PaymentAdapter paymentAdapter = new PaymentAdapter(paymentService);

        // Facade Setup
        OrderProcessor orderProcessor = OrderProcessor.getInstance();
        OrderServiceFacade orderServiceFacade = new OrderServiceFacade(orderProcessor, paymentAdapter);

        // Process Order with Payment through Facade
        orderServiceFacade.processOrderWithPayment(giftWrappedInsuredBookOrder, 100.0);

        // Example of Journal Order with Decoration
        JournalOrderFactory journalFactory = new JournalOrderFactory();
        Order journalOrder = new OrderBuilder()
                .setOrderType(journalFactory.createOrder())
                .setOrderDetails("Science Journal", 5)
                .build();
        Order insuredJournalOrder = new InsuredOrderDecorator(journalOrder);
        orderServiceFacade.processOrderWithPayment(insuredJournalOrder, 50.0);
    }
}
