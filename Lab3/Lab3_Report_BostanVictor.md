# Ministry of Education, Culture and Research of the Republic of Moldova

## Technical University of Moldova

### Department of Software and Automation Engineering

---

**REPORT**

Laboratory work No. 3

**Discipline**: Techniques and Mechanisms of Software Design

---

**Elaborated:**  
&nbsp;&nbsp;&nbsp;&nbsp;FAF-222, Bostan Victor

**Checked:**  
&nbsp;&nbsp;&nbsp;&nbsp;asist. univ.,  
&nbsp;&nbsp;&nbsp;&nbsp;Furdui Alexandru

---

Chișinău 2024

---

## Topic: Behavioral Design Patterns

### Objectives

1. **Study and understand the Behavioral Design Patterns.**
2. **As a continuation of the previous laboratory work, think about what communication between software entities might be involed in your system.**
3. **Implement some additional functionalities using behavioral design patterns.**

## Theory

Behavioral design patterns are one of the three main types of design patterns (alongside structural and creational patterns) and primarily focus on managing object interactions and responsibilities. They define how objects communicate within a system, emphasizing delegation, flexibility, and maintainable object relationships. This makes them essential for creating flexible systems that are easy to extend, modify, and debug.
The Behavioral Design Patterns include:

- **Chain of Responsibility**: Passes requests along a chain where each handler can either process or forward the request, decoupling senders from receivers.
- **Command**: Encapsulates a request as an object to support parameterization, queuing, and undoable operations.
- **Iterator**: Provides sequential access to elements of a collection without exposing its internal structure.
- **Mediator**: Manages communication between objects to reduce dependencies and improve modularity.
- **Memento**: Captures and restores an object's internal state for undo/redo functionality.
- **Observer**: Establishes a one-to-many dependency, notifying all observers when the subject's state changes.
- **State**: Allows an object to change its behavior when its internal state changes, using state-specific classes.
- **Strategy**: Defines interchangeable algorithms, enabling dynamic switching between different methods of operation.
- **Template Method**: Defines an algorithm's structure in a superclass, allowing subclasses to override specific steps.
- **Visitor**: Adds operations to objects without modifying them by defining a separate visitor for additional functionality.

## Introduction

For this laboratory work we had the task to extend our project from previous labs with some behavioral design patterns. I decided to implement 2 such patterns: `Observer` and `Strategy`; since I thought that implementing them made the most sense for my specific project.

## Implementation

### Observer Pattern

I decided to use the `Observer` pattern in order for objects to be notified whenever an order's status changes. This pattern is needed when you want to implement functionalities that need to respond to events (such as logging or notifying a customer) independently of the primary object handling the order. So, in my case, when the `Observer` pattern wasn't implemented, events like notifying a user that a order has been processed were done in `OrderProcessor` class, which is not very good because it shouldn't be a functionality of the `OrderProcessor`.

The way that I implemented the `Observer` pattern in my project is that I first created a `Observer` interface that all concerete observers will have to implement. Then, I created 2 concrete observers: `OrderLogger`, for logging the order status in the console; and `CustomerNotifier`, for notifying the customer on the order status. Also, I create a `OrderStatusNotifier` class which manages a list of observers by being able to add or remove observers. It also is responsible for going through the list of observers, when the `notifyObservers` method is called, and call the `update` method of each observer. In the end, I had to modify the `OrderProcessor` class to use the notifier.

#### Code Snippet

**Location**: `domain.observer`

**Observer Interface**

```java
package domain.observer;

public interface Observer {
    void update(String message);
}
```

**Order Logger**

```java
package domain.observer;

public class OrderLogger implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Logging Order Status Update: " + message);
    }
}
```

**CustomerNotifier**

```java
package domain.observer;

public class CustomerNotifier implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Notifying Customer: " + message);
    }
}
```

**OrderStatusNotifier**

```java
package domain.observer;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusNotifier {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
```

**Location**: `domain.singleton`

**Order Processor**

```java
package domain.singleton;

import domain.models.order.Order;
import domain.observer.Observer;
import domain.observer.OrderStatusNotifier;

public class OrderProcessor {
    private static OrderProcessor instance;
    private OrderStatusNotifier notifier;

    private OrderProcessor() {
        notifier = new OrderStatusNotifier();
    }

    public static OrderProcessor getInstance() {
        if (instance == null) {
            instance = new OrderProcessor();
        }
        return instance;
    }

    public void addObserver(Observer observer) {
        notifier.addObserver(observer);
    }

    public void process(Order order) {
        order.processOrder();
        notifier.notifyObservers("Order processed: " + order.getTitle() + ", Quantity: " + order.getQuantity());
    }
}
```
### Strategy Pattern

The `Strategy` pattern is used when there are multiple ways to perform an operation  that should be selected at runtime and not with primitive checks in classes. An example of this, which I have in my projecy, is the payment system. I implemented different payment types to allow clients to decide the payment type when processing an order.

To implement the Strategy pattern, I created a `PaymentStrategy` interface, which defines the `pay` method. Each concrete payment method, such as `CreditCardPayment` and `PayPalPayment`, implements this interface and provides its own version of the pay logic. This way, each payment method can be done in the `OrderServiceFacade`, which then calls the pay method on the provided `PaymentStrategy`. Also, since I used previously the `Adapter` pattern for `ExternalPayments`, I made sure that the `Strategy` pattern can work alongside it in `OrderServiceFacade`. So, now the client can either use the internal payment methods implemented with the `Strategy` pattern or an arbitrary external payment method done with the `Adapter` pattern.

#### Code Snippets

**Location**: `domain.strategy`

**PaymentStrategy Interface**

```java
package domain.strategy;

public interface PaymentStrategy {
    boolean pay(double amount);
}
```

**CreditCardPayment**

```java
package domain.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        return true;
    }
}
```

**PayPalPayment**

```java
package domain.strategy;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        return true;
    }
}
```

**Location**: `domain.facade`

**OrderServiceFacade**

```java
package domain.facade;

import domain.models.order.Order;
import domain.singleton.OrderProcessor;
import domain.strategy.PaymentStrategy;

public class OrderServiceFacade {
    private final OrderProcessor orderProcessor;

    public OrderServiceFacade(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    public void processOrderWithPayment(Order order, double amount, PaymentStrategy paymentStrategy) {
        orderProcessor.process(order);

        boolean paymentSuccessful = paymentStrategy.pay(amount);

        if (paymentSuccessful) {
            System.out.println("Order processed and payment successful.");
        } else {
            System.out.println("Payment failed.");
        }
    }

    // Method for handling orders with external payment services through the Adapter pattern
    public void processOrderWithExternalPayment(Order order, double amount, PaymentStrategy paymentAdapter) {
        orderProcessor.process(order);

        boolean paymentSuccessful = paymentAdapter.pay(amount);

        if (paymentSuccessful) {
            System.out.println("Order processed and payment successful through external service.");
        } else {
            System.out.println("Payment failed through external service.");
        }
    }
}
```

## Results

Here is the `Main` class I used to test the implementations:

```java
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
```

And here are the results:

```vbnet
Processing book order for: Java Programming Book, Quantity: 10
Order is insured.
Order is gift-wrapped.
Logging Order Status Update: Order processed: null, Quantity: 0
Notifying Customer: Order processed: null, Quantity: 0
Processing credit card payment of $100.0
Order processed and payment successful.

---

Processing journal order for: Science Journal, Quantity: 5
Order is insured.
Logging Order Status Update: Order processed: null, Quantity: 0
Notifying Customer: Order processed: null, Quantity: 0
Processing PayPal payment of $50.0
Order processed and payment successful.

---

Processing magazine order for: Tech Magazine, Quantity: 3
Order is insured.
Logging Order Status Update: Order processed: null, Quantity: 0
Notifying Customer: Order processed: null, Quantity: 0
Processing payment of $30.0 through external service.
Order processed and payment successful through external service.
```

## Conclusion

In this lab I explored Behavioral Design Patterns, more specifically `Observer` and `Strategy` patterns, which helped to increase the functionality of the project. The `Observer` pattern helped in "easing" the work of the `OrderProcessor` by separating the "notification" functionality. And the `Strategy` pattern enabled dynamic payment method selection, allowing different payment options to be handled independently.
