package client;

import domain.factory.BookOrderFactory;
import domain.factory.JournalOrderFactory;
import domain.factory.MagazineOrderFactory;
import domain.models.builder.OrderBuilder;
import domain.models.order.Order;
import domain.singleton.OrderProcessor;

public class Main {
    public static void main(String[] args) {
        // Book Order Example
        BookOrderFactory bookFactory = new BookOrderFactory();
        Order bookOrder = new OrderBuilder()
                .setOrderType(bookFactory.createOrder())
                .setOrderDetails("Java Programming Book", 10)
                .build();

        // Journal Order Example
        JournalOrderFactory journalFactory = new JournalOrderFactory();
        Order journalOrder = new OrderBuilder()
                .setOrderType(journalFactory.createOrder())
                .setOrderDetails("Science Journal", 5)
                .build();

        // Magazine Order Example
        MagazineOrderFactory magazineFactory = new MagazineOrderFactory();
        Order magazineOrder = new OrderBuilder()
                .setOrderType(magazineFactory.createOrder())
                .setOrderDetails("Tech Magazine", 7)
                .build();

        // Process Orders using Singleton OrderProcessor
        OrderProcessor orderProcessor = OrderProcessor.getInstance();
        orderProcessor.process(bookOrder);
        orderProcessor.process(journalOrder);
        orderProcessor.process(magazineOrder);

        // Clone (Prototype) Pattern Tests
        System.out.println("\nTesting Cloning (Prototype Pattern):");

        // Clone Book Order
        Order clonedBookOrder = bookOrder.clone();
        clonedBookOrder.setTitle("Advanced Java Programming Book");  // Changing title to differentiate
        clonedBookOrder.setQuantity(5);  // Changing quantity for the cloned order
        orderProcessor.process(clonedBookOrder); // Process the cloned order

        // Clone Journal Order
        Order clonedJournalOrder = journalOrder.clone();
        clonedJournalOrder.setTitle("Physics Journal");
        clonedJournalOrder.setQuantity(3);
        orderProcessor.process(clonedJournalOrder);

        // Clone Magazine Order
        Order clonedMagazineOrder = magazineOrder.clone();
        clonedMagazineOrder.setTitle("Health Magazine");
        clonedMagazineOrder.setQuantity(2);
        orderProcessor.process(clonedMagazineOrder);
    }
}
