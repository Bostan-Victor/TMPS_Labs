Ministry of Education, Culture and Research of the Republic of Moldova

Technical University of Moldova

Department of Software and Automation Engineering





**REPORT**

Laboratory work No. 1

**Discipline**: Techniques and Mechanisms of Software Design



Elaborated:							   	  FAF-222, Bostan Victor 


Checked:					 			      asist. univ., 

Furdui Alexandru






Chișinău 2024

<a name="_toc146122315"></a>**Topic: Creational Design Patterns**

**Objectives:**

1. **Study and understand the Creational Design Patterns.**
1. **Choose a domain, define its main classes/models/entities and choose the appropriate instantiation mechanisms.**
1. **Use some creational design patterns for object instantiation in a sample project.**

**Main tasks:**

1. **Choose an OO programming language and a suitable IDE or Editor (No frameworks/libs/engines allowed).**
1. **Select a domain area for the sample project.**
1. **Define the main involved classes and think about what instantiation mechanisms are needed.**
1. **Based on the previous point, implement at least 3 creational design patterns in your project.**

**THEORY**

In software engineering, creational design patterns offer established solutions for handling object creation in a flexible and efficient manner. When directly instantiating objects, applications often face design issues, increased coupling, and reduced flexibility. Creational patterns help mitigate these issues by controlling and optimizing the creation process, hiding the instantiation complexity from the user, and providing suitable creation mechanisms for different use cases.

Some of the most commonly used creational design patterns include:

- **Singleton Pattern:** This pattern ensures that a class has only one instance, providing a global point of access to it. This is often used in cases where a single, shared resource or manager is needed across an application, such as a configuration manager or database connection pool. By enforcing a single instance, the Singleton pattern helps prevent unintended object duplication and the inconsistencies that could arise from it.
- **Builder Pattern:** The Builder pattern allows the construction of complex objects step-by-step, separating the construction process from the representation. It is particularly useful for objects that require multiple parameters or have various configurations, as it provides a flexible way to create them without the need for a complex constructor. By using setters and chainable methods, the Builder pattern offers a readable and flexible way to create objects with specific configurations.
- **Prototype Pattern:** This pattern is used when creating a new instance of an object by copying, or "cloning," an existing instance. It allows the creation of objects based on a template of an existing object, ensuring minimal cost and efficiency. The Prototype pattern is beneficial when the cost of creating an object is high or when instances need to be initialized with the same state as an existing one.
- **Factory Method Pattern:** This pattern provides an interface for creating objects in a superclass but allows subclasses to alter the type of object that will be created. It enables a class to delegate the instantiation responsibility to subclasses, thus allowing flexibility in creating object types without altering the client code.
- **Abstract Factory Pattern:** The Abstract Factory pattern provides an interface for creating families of related or dependent objects without specifying their concrete classes. This pattern is useful when an application needs to use various objects from different families interchangeably. By abstracting the object creation, it facilitates consistent and interchangeable usage of families of objects.
- **Object Pool Pattern:** This pattern manages a pool of reusable objects, especially when object creation is expensive. Instead of creating and destroying objects repeatedly, the Object Pool pattern allows reusing objects, optimizing resource usage. It’s commonly used in scenarios requiring frequent, repetitive object creation, such as database connections or thread pools.

**INTRODUCTION**

The implemented system represents an order processing system for various publication types, such as books, journals, and magazines. It simulates the lifecycle of creating, configuring, processing, and duplicating publication orders, utilizing creational design patterns to manage object instantiation in a modular and efficient way. This project implements three creational design patterns: Singleton, Builder, and Prototype.

1. Singleton Pattern:
- Class: OrderProcessor
- Purpose: Ensures a single instance of OrderProcessor is used to process all orders. The Singleton pattern provides a controlled way to manage order processing in the application by restricting instantiation and providing a global point of access to this processing unit.
1. Builder Pattern:
- Class: OrderBuilder
- Purpose: Provides a flexible way to create Order objects with specific details, such as title and quantity, through chaining methods. The Builder pattern offers clarity and control in constructing orders with various attributes, avoiding the need for complex constructors.
1. Prototype Pattern:
- Class: Order
- Purpose: Allows cloning of existing orders with the clone() method, providing a way to create new instances based on an existing template. This is beneficial for creating similar orders with slight modifications, optimizing the instantiation process by copying existing objects instead of creating them from scratch.

**IMPLEMENTATION**

The Singleton pattern is used in the OrderProcessor class to ensure a single instance manages all order processing in the system. By making the constructor private and providing a static method for instance retrieval, we ensure that only one OrderProcessor exists throughout the application.

*package domain.singleton;*

*import domain.models.order.Order;*

*public class OrderProcessor {*

`    `*private static OrderProcessor instance;*

`    `*private OrderProcessor() {}  // Private constructor to restrict instantiation*

`    `*public static OrderProcessor getInstance() {*

`        `*if (instance == null) {*

`            `*instance = new OrderProcessor();  // Create instance if not already created*

`        `*}*

`        `*return instance;*

`    `*}*

`    `*public void process(Order order) {*

`        `*order.processOrder();  // Processes the order using the provided details*

`    `*}*

*}*

In Main, we retrieve the single OrderProcessor instance and use it to process orders:

*OrderProcessor orderProcessor = OrderProcessor.getInstance();*

*orderProcessor.process(bookOrder);*

*orderProcessor.process(journalOrder);*

*orderProcessor.process(magazineOrder);*

The Builder pattern is applied in the OrderBuilder class to construct Order objects. This pattern provides a step-by-step approach to setting order details, ensuring clarity and flexibility in object creation. The setOrderType and setOrderDetails methods are chainable, allowing us to configure each order with specific attributes.

*package domain.models.builder;*

*import domain.models.order.Order;*

*public class OrderBuilder {*

`    `*private Order order;*

`    `*public OrderBuilder setOrderType(Order order) {*

`        `*this.order = order;  // Sets the type of order (Book, Journal, Magazine)*

`        `*return this;*

`    `*}*

`    `*public OrderBuilder setOrderDetails(String title, int quantity) {*

`        `*this.order.setTitle(title);*

`        `*this.order.setQuantity(quantity);  // Sets title and quantity details*

`        `*return this;*

`    `*}*

`    `*public Order build() {*

`        `*return order;  // Returns the fully configured order*

`    `*}*

*}*

In Main, we use OrderBuilder to create different types of orders:

*Order bookOrder = new OrderBuilder()*

`        `*.setOrderType(bookFactory.createOrder())*

`        `*.setOrderDetails("Java Programming Book", 10)*

`        `*.build();*

*Order journalOrder = new OrderBuilder()*

`        `*.setOrderType(journalFactory.createOrder())*

`        `*.setOrderDetails("Science Journal", 5)*

`        `*.build();*

The Prototype pattern is used to clone existing orders, allowing the creation of new orders based on an existing template. The Order class implements Cloneable and overrides the clone() method to create a deep copy of an order object. This is particularly useful when new orders need similar attributes to existing ones.

*package domain.models.order;*

*public abstract class Order implements Cloneable {*

`    `*private String title;*

`    `*private int quantity;*

`    `*// Getters and setters omitted for brevity*

`    `*public abstract void processOrder();*

`    `*// Clone method for Prototype pattern*

`    `*@Override*

`    `*public Order clone() {*

`        `*try {*

`            `*return (Order) super.clone();  // Clones the existing order*

`        `*} catch (CloneNotSupportedException e) {*

`            `*e.printStackTrace();*

`            `*return null;*

`        `*}*

`    `*}*

*}*

In Main, we clone existing orders and make slight modifications before processing them:

*Order clonedBookOrder = bookOrder.clone();*

*clonedBookOrder.setTitle("Advanced Java Programming Book");  // Modify title for clone*

*clonedBookOrder.setQuantity(5);  // Modify quantity*

*orderProcessor.process(clonedBookOrder);*

**CONCLUSION**

In this project, the Singleton, Builder, and Prototype patterns were implemented to streamline the order creation and processing in a publication ordering system. The Singleton pattern ensures a single instance of `OrderProcessor` to manage all orders centrally, maintaining consistency and efficiency. The Builder pattern simplifies the creation of orders by providing a flexible, readable way to configure details without complex constructors. The Prototype pattern further optimizes the system by allowing for the cloning of orders, making it easy to create new orders based on existing ones with minimal adjustments. Together, these patterns enhance the system’s modularity, maintainability, and scalability, highlighting the practical benefits of creational design patterns in object-oriented design.