# Ministry of Education, Culture and Research of the Republic of Moldova

## Technical University of Moldova

### Department of Software and Automation Engineering

---

**REPORT**

Laboratory work No. 1

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

## Topic: Creational Design Patterns

### Objectives

1. **Study and understand the Creational Design Patterns.**
2. **Choose a domain, define its main classes/models/entities and choose the appropriate instantiation mechanisms.**
3. **Use some creational design patterns for object instantiation in a sample project.**

### Main tasks

1. **Choose an OO programming language and a suitable IDE or Editor (No frameworks/libs/engines allowed).**
2. **Select a domain area for the sample project.**
3. **Define the main involved classes and think about what instantiation mechanisms are needed.**
4. **Based on the previous point, implement at least 3 creational design patterns in your project.**

---

## Theory

In software engineering, creational design patterns offer established solutions for handling object creation in a flexible and efficient manner. When directly instantiating objects, applications often face design issues, increased coupling, and reduced flexibility. Creational patterns help mitigate these issues by controlling and optimizing the creation process, hiding the instantiation complexity from the user, and providing suitable creation mechanisms for different use cases.

Some commonly used creational design patterns include:

- **Singleton Pattern**: Ensures a class has only one instance, providing a global point of access. Commonly used for shared resources, like a configuration manager.
- **Builder Pattern**: Allows step-by-step construction of complex objects, separating construction from representation. Useful for objects requiring multiple parameters or configurations.
- **Prototype Pattern**: Creates a new instance by cloning an existing one, which is efficient when object creation is costly or initialization needs to mirror an existing object.
- **Factory Method Pattern**: Provides an interface for object creation in a superclass, allowing subclasses to define which object types will be created.
- **Abstract Factory Pattern**: Defines an interface for creating families of related objects without specifying concrete classes, supporting interchangeable use of object families.
- **Object Pool Pattern**: Manages a pool of reusable objects, optimizing resource usage by reusing rather than repeatedly creating and destroying objects.

---

## Introduction

The implemented system represents an order processing system for various publication types, such as books, journals, and magazines. It simulates the lifecycle of creating, configuring, processing, and duplicating publication orders, utilizing creational design patterns to manage object instantiation in a modular and efficient way. This project implements three creational design patterns: Singleton, Builder, and Prototype.

1. **Singleton Pattern**  
   - **Class**: `OrderProcessor`
   - **Purpose**: Ensures a single instance of `OrderProcessor` processes all orders. It restricts instantiation and provides a global point of access to this processing unit.

2. **Builder Pattern**  
   - **Class**: `OrderBuilder`
   - **Purpose**: Provides a flexible way to create `Order` objects with details like title and quantity. It enables clarity and control in constructing orders without complex constructors.

3. **Prototype Pattern**  
   - **Class**: `Order`
   - **Purpose**: Allows cloning of existing orders with the `clone()` method, making it easy to create new instances with slight modifications from an existing template.

---

## Implementation

### Singleton Pattern

The Singleton pattern is used in the `OrderProcessor` class to ensure a single instance manages all order processing in the system. By making the constructor private and providing a static method for instance retrieval, we ensure that only one `OrderProcessor` exists throughout the application.

```java
package domain.singleton;

import domain.models.order.Order;

public class OrderProcessor {
    private static OrderProcessor instance;

    private OrderProcessor() {}  // Private constructor to restrict instantiation

    public static OrderProcessor getInstance() {
        if (instance == null) {
            instance = new OrderProcessor();  // Create instance if not already created
        }
        return instance;
    }

    public void process(Order order) {
        order.processOrder();  // Processes the order using the provided details
    }
}
```

In `Main`, we retrieve the single `OrderProcessor` instance and use it to process orders:

```java
OrderProcessor orderProcessor = OrderProcessor.getInstance();
orderProcessor.process(bookOrder);
orderProcessor.process(journalOrder);
orderProcessor.process(magazineOrder);
```
### Builder Pattern

The Builder pattern is applied in the `OrderBuilder` class to construct `Order` objects. This pattern provides a step-by-step approach to setting order details, ensuring clarity and flexibility in object creation. The `setOrderType` and `setOrderDetails` methods are chainable, allowing us to configure each order with specific attributes.

```java
package domain.models.builder;

import domain.models.order.Order;

public class OrderBuilder {
    private Order order;

    public OrderBuilder setOrderType(Order order) {
        this.order = order;  // Sets the type of order (Book, Journal, Magazine)
        return this;
    }

    public OrderBuilder setOrderDetails(String title, int quantity) {
        this.order.setTitle(title);
        this.order.setQuantity(quantity);  // Sets title and quantity details
        return this;
    }

    public Order build() {
        return order;  // Returns the fully configured order
    }
}
```

In `Main`, we use `OrderBuilder` to create different types of orders:
```java
Order bookOrder = new OrderBuilder()
        .setOrderType(bookFactory.createOrder())
        .setOrderDetails("Java Programming Book", 10)
        .build();

Order journalOrder = new OrderBuilder()
        .setOrderType(journalFactory.createOrder())
        .setOrderDetails("Science Journal", 5)
        .build();
```

### Prototype Pattern

The Prototype pattern is used to clone existing orders, allowing the creation of new orders based on an existing template. The `Order` class implements `Cloneable` and overrides the `clone()` method to create a deep copy of an order object. This is particularly useful when new orders need similar attributes to existing ones.

```java
package domain.models.order;

public abstract class Order implements Cloneable {
    private String title;
    private int quantity;

    // Getters and setters omitted for brevity
    public abstract void processOrder();

    // Clone method for Prototype pattern
    @Override
    public Order clone() {
        try {
            return (Order) super.clone();  // Clones the existing order
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
```
In `Main`, we clone existing orders and make slight modifications before processing them:

```java
Order clonedBookOrder = bookOrder.clone();
clonedBookOrder.setTitle("Advanced Java Programming Book");  // Modify title for clone
clonedBookOrder.setQuantity(5);  // Modify quantity
orderProcessor.process(clonedBookOrder);
```

---

## Conclusion

In this project, the Singleton, Builder, and Prototype patterns were implemented to streamline the order creation and processing in a publication ordering system. The Singleton pattern ensures a single instance of `OrderProcessor` to manage all orders centrally, maintaining consistency and efficiency. The Builder pattern simplifies the creation of orders by providing a flexible, readable way to configure details without complex constructors. The Prototype pattern further optimizes the system by allowing for the cloning of orders, making it easy to create new orders based on existing ones with minimal adjustments. Together, these patterns enhance the system’s modularity, maintainability, and scalability, highlighting the practical benefits of creational design patterns in object-oriented design.
