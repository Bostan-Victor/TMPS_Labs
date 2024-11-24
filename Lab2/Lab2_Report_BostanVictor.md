# Ministry of Education, Culture and Research of the Republic of Moldova

## Technical University of Moldova

### Department of Software and Automation Engineering

---

**REPORT**

Laboratory work No. 2

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

## Topic: Structural Design Patterns

### Objectives

1. **Study and understand the Structural Design Patterns.**
2. **Expand on the previous project by adding new functionalities.**
3. **Implement structural design patterns to add additional features.**

---

## Introduction

Structural design models help manage relationships between objects and classes. It provides flexibility and efficiency in object structure. Particularly useful in complex systems. Optimizing or simplifying object interactions greatly improves the maintainability and scalability of the code.

I have expanded the existing order processing system for the previous lab with the addition of three structural design variations: Adapter, Decorator, and Facade. Each model introduces additional functionality that expands the capabilities of the lab. system without directly modifying existing classes.

---

## Theory

In software engineering, structural design patterns provide reusable solutions for structuring relationships between objects or classes, often focusing on flexibility through composition rather than inheritance.

### Overview of Implemented Patterns:

- **Adapter Pattern**: Converts an interface of a class into another interface that the client expects, allowing otherwise incompatible interfaces to work together.
- **Decorator Pattern**: Adds additional responsibilities to an object dynamically, providing a flexible alternative to subclassing for extending functionality.
- **Facade Pattern**: Simplifies client interaction by providing a unified interface to a complex subsystem, reducing dependencies and promoting modular design.

---

## Implementation

### Adapter Pattern

I implemented Adapter pattern to integrate an external payment service into the system. Since the `ExternalPaymentService` has a different interface than expected by the `OrderServiceFacade`, I used an adapter to standardize payment processing.

#### Code Snippet
**Location**: `domain.adapter.PaymentAdapter`

```java
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
```

The adapter enables the `ExternalPaymentService` to be used smoothly within my system, creating a bridge between the order processing and payment subsystems.

### Decorator Pattern

I chose Decorator pattern to add optional features, like insurance and gift wrapping, to `Order` objects without modifying the core `Order` class. This approach improves flexibility by enabling feature additions dynamically.

#### Code Snippet
**Location**: `domain.decorator`

**Insured Order Decorator**

```java
package domain.decorator;

import domain.models.order.Order;

public class InsuredOrderDecorator extends OrderDecorator {
    public InsuredOrderDecorator(Order order) {
        super(order);
    }

    @Override
    public void processOrder() {
        decoratedOrder.processOrder();
        System.out.println("Order is insured.");
    }
}
```

**Gift Wrapped Order Decorator**

```java
package domain.decorator;

import domain.models.order.Order;

public class GiftWrappedOrderDecorator extends OrderDecorator {
    public GiftWrappedOrderDecorator(Order order) {
        super(order);
    }

    @Override
    public void processOrder() {
        decoratedOrder.processOrder();
        System.out.println("Order is gift-wrapped.");
    }
}
```

By using these decorators, the client can dynamically add features like insurance or gift wrapping to any order without altering the `Order` class.

### Facade Pattern

I used the Facade pattern to make the order processing and payment flow easier by combining several complex steps into one `OrderServiceFacade` class. This single, simplified interface makes it easier for the client to use and keeps the code more organized.

#### Code Snippet
**Location**: `domain.facade.OrderServiceFacade`

```java
package domain.facade;

import domain.adapter.PaymentAdapter;
import domain.models.order.Order;
import domain.singleton.OrderProcessor;

public class OrderServiceFacade {
    private final OrderProcessor orderProcessor;
    private final PaymentAdapter paymentAdapter;

    public OrderServiceFacade(OrderProcessor orderProcessor, PaymentAdapter paymentAdapter) {
        this.orderProcessor = orderProcessor;
        this.paymentAdapter = paymentAdapter;
    }

    public void processOrderWithPayment(Order order, double amount) {
        orderProcessor.process(order);
        boolean paymentSuccessful = paymentAdapter.pay(amount);
        if (paymentSuccessful) {
            System.out.println("Order processed and payment successful.");
        } else {
            System.out.println("Payment failed.");
        }
    }
}
```

### Main Code Integration

Here is what the `Main` class looks like now:
 
```java
package client;

import domain.adapter.PaymentAdapter;
import domain.decorator.GiftWrappedOrderDecorator;
import domain.decorator.InsuredOrderDecorator;
import domain.external.ExternalPaymentService;
import domain.facade.OrderServiceFacade;
import domain.factory.BookOrderFactory;
import domain.models.builder.OrderBuilder;
import domain.models.order.Order;
import domain.singleton.OrderProcessor;

public class Main {
    public static void main(String[] args) {
        BookOrderFactory bookFactory = new BookOrderFactory();
        Order bookOrder = new OrderBuilder()
                .setOrderType(bookFactory.createOrder())
                .setOrderDetails("Java Programming Book", 10)
                .build();

        // Applying Decorators
        Order insuredBookOrder = new InsuredOrderDecorator(bookOrder);
        Order giftWrappedInsuredBookOrder = new GiftWrappedOrderDecorator(insuredBookOrder);

        // Adapter and Facade Setup
        ExternalPaymentService paymentService = new ExternalPaymentService();
        PaymentAdapter paymentAdapter = new PaymentAdapter(paymentService);
        OrderProcessor orderProcessor = OrderProcessor.getInstance();
        OrderServiceFacade orderServiceFacade = new OrderServiceFacade(orderProcessor, paymentAdapter);

        // Process Order with Payment through Facade
        orderServiceFacade.processOrderWithPayment(giftWrappedInsuredBookOrder, 100.0);
    }
}
```

## Results

When I run the code, it shows the processing of a book order that includes insurance and gift wrapping, with a successful payment. This output confirms that the structural design patterns were implemented effectively.

```vbnet
Processing book order for: Java Programming Book, Quantity: 10
Order is insured.
Order is gift-wrapped.
Processing payment of $100.0 through external service.
Order processed and payment successful.
```

## Conclusion

In this lab, I looked at how structural design patterns—Adapter, Decorator, and Facade—can improve my order processing system by adding new features. Using these patterns helped me see how they can simplify complex tasks, make the code more organized, and add flexibility. The Adapter pattern let me easily connect an external payment service, while the Decorator pattern allowed me to add options like insurance and gift wrapping to orders without changing the main `Order` class. Lastly, the Facade pattern provided a simpler interface for handling orders and payments, making it easier for the client to interact with the system. Overall, this lab showed how structural patterns make system design more flexible, organized, and easier to maintain.
