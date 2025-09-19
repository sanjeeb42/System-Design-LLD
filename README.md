# Strategy Design Pattern Explained 

## 📖 Overview

The **Strategy Design Pattern** is a behavioral design pattern that allows you to define a family of algorithms, encapsulate each one, and make them interchangeable at runtime. This pattern lets the algorithm vary independently from the clients that use it.

## 🎯 Problem Solved

Imagine you're building a payment system for an e-commerce application. You need to support multiple payment methods:
- UPI (Unified Payments Interface)
- Credit/Debit Cards
- Net Banking

Without the Strategy pattern, you might end up with messy conditional statements scattered throughout your code. Every time you add a new payment method, you'd need to modify existing code, violating the **Open/Closed Principle**.

## 💡 Solution: Strategy Pattern

The Strategy pattern solves this by:
1. **Defining a common interface** for all payment strategies
2. **Creating separate classes** for each payment method
3. **Allowing runtime switching** between different strategies
4. **Keeping the client code clean** and strategy-agnostic

## 🏗️ Pattern Structure

```
                    ┌─────────────────────┐
                    │   PaymentStrategy   │ ◄─── Strategy Interface
                    │      <<interface>>  │
                    │                     │
                    │ + pay(amount: double)│
                    └─────────────────────┘
                              ▲
                              │ implements
                    ┌─────────┼─────────┐
                    │         │         │
         ┌─────────────────┐ │ ┌─────────────────────┐ ┌─────────────────────────┐
         │UpiPaymentStrategy│ │ │CreditCardPayment    │ │NetBankingPayment        │
         │                 │ │ │Strategy             │ │Strategy                 │
         │- mobile: String │ │ │- cardNumber: String │ │- bankName: String       │
         │+ pay(amount)    │ │ │+ pay(amount)        │ │- accountHolder: String  │
         └─────────────────┘ │ └─────────────────────┘ │+ pay(amount)            │
                             │                         └─────────────────────────┘
                    ┌─────────────────────┐
                    │       Order         │ ◄─── Context Class
                    │                     │
                    │- paymentStrategy    │
                    │- total: double      │
                    │+ setPaymentStrategy │
                    │+ processPayment()   │
                    └─────────────────────┘
```

## 📝 Implementation Example

### 1. Strategy Interface
```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

### 2. Concrete Strategies

**UPI Payment Strategy:**
```java
public class UpiPaymentStrategy implements PaymentStrategy {
    private String mobile;
    
    public UpiPaymentStrategy(String mobile) {
        this.mobile = mobile;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI (" + mobile + ")");
    }
}
```

**Credit Card Payment Strategy:**
```java
public class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    
    public CreditCardPaymentStrategy(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Credit Card (" + cardNumber + ")");
    }
}
```

**Net Banking Payment Strategy:**
```java
public class NetBankingPaymentStrategy implements PaymentStrategy {
    private String bankName;
    private String accountHolderName;
    
    public NetBankingPaymentStrategy(String bankName, String accountHolderName) {
        this.bankName = bankName;
        this.accountHolderName = accountHolderName;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Net Banking (" + bankName + " - " + accountHolderName + ")");
    }
}
```

### 3. Context Class (Order)
```java
public class Order {
    private PaymentStrategy paymentStrategy;
    private double total;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    public boolean processPayment() {
        if (paymentStrategy != null) {
            paymentStrategy.pay(total);
            return true;
        }
        return false;
    }
}
```

## 🚀 Usage Examples

### Basic Usage
```java
// Create different payment strategies
PaymentStrategy upi = new UpiPaymentStrategy("9876543210");
PaymentStrategy card = new CreditCardPaymentStrategy("**** **** **** 1234");
PaymentStrategy netBanking = new NetBankingPaymentStrategy("HDFC Bank", "John Doe");

// Use with orders
Order order1 = zomato.checkoutNow(user, "Delivery", upi);
Order order2 = zomato.checkoutNow(user, "Delivery", card);
Order order3 = zomato.checkoutNow(user, "Delivery", netBanking);
```

### Runtime Strategy Switching
```java
Order order = new Order();
order.setPaymentStrategy(new UpiPaymentStrategy("1234567890"));

// Later, user decides to switch payment method
order.setPaymentStrategy(new CreditCardPaymentStrategy("**** 5678"));

// Process payment with the new strategy
order.processPayment();
```

## 📁 Repository Structure

This repository contains multiple examples of the Strategy Design Pattern:

### Payment Strategy Implementation (Main Example)
- **Location**: `zomato-food-delivery/src/main/java/strategies/`
- **Files**:
  - `PaymentStrategy.java` - Strategy interface
  - `UpiPaymentStrategy.java` - UPI payment implementation
  - `CreditCardPaymentStrategy.java` - Credit card implementation
  - `NetBankingPaymentStrategy.java` - Net banking implementation
- **Demo**: `zomato-food-delivery/src/main/java/org/example/SimplePaymentDemo.java`

### Other Strategy Examples
- **Location**: `Strategy Design Pattern/`
- **Contains**: Robot behavior strategies (Flyable, Walkable, Talkable)

## 🏃‍♂️ Running the Demo

To see the Strategy pattern in action:

```bash
# Compile the project
cd zomato-food-delivery
mvn compile

# Run the main demo
mvn exec:java -Dexec.mainClass="org.example.Main"

# Run the payment strategy demo
mvn exec:java -Dexec.mainClass="org.example.PaymentStrategyDemo"
```

### Sample Output
```
=== Strategy Design Pattern Demo ===
User: Sanjeeb is ordering food.

Selected restaurant: Bikaner
Items in cart:
------------------------------------
P1 : Chole Bhature : ₹120
P2 : Samosa : ₹15
------------------------------------
Grand total : ₹135.0

=== Demonstrating Different Payment Strategies ===

1. Using UPI Payment Strategy:
   - Strategy: UpiPaymentStrategy
   - Implementation: Mobile number based payment
Paid ₹135.0 using UPI (9876543210)

2. Using Credit Card Payment Strategy:
   - Strategy: CreditCardPaymentStrategy
   - Implementation: Card number based payment
Paid ₹120.0 using Credit Card (**** **** **** 1234)

3. Using Net Banking Payment Strategy:
   - Strategy: NetBankingPaymentStrategy
   - Implementation: Bank name and account holder based payment
Paid ₹15.0 using Net Banking (HDFC Bank - Sanjeeb Kumar)
```

## ✅ Benefits of Strategy Pattern

### 1. **Open/Closed Principle**
- Open for extension: Easy to add new payment methods
- Closed for modification: No need to change existing code

### 2. **Single Responsibility Principle**  
- Each payment strategy has one responsibility
- Payment logic is separated from business logic

### 3. **Runtime Flexibility**
- Can switch between strategies at runtime
- Strategies can be selected based on user preferences or conditions

### 4. **Code Reusability**
- Payment strategies can be reused across different contexts
- Common interface ensures consistency

### 5. **Easy Testing**
- Each strategy can be tested independently
- Mock strategies can be easily created for testing

## 🌍 Real-World Use Cases

### E-commerce Platforms
- **Payment Methods**: Credit Card, PayPal, Apple Pay, Google Pay
- **Shipping**: Standard, Express, Overnight delivery
- **Pricing**: Regular pricing, member discounts, seasonal offers

### Navigation Apps
- **Route Calculation**: Fastest route, shortest route, avoid tolls
- **Transportation**: Walking, driving, public transport

### Data Processing
- **Sorting Algorithms**: QuickSort, MergeSort, BubbleSort
- **Compression**: ZIP, RAR, 7Z
- **Encryption**: AES, DES, RSA

### Gaming
- **AI Behaviors**: Aggressive, Defensive, Balanced
- **Difficulty Levels**: Easy, Medium, Hard
- **Character Abilities**: Different attack patterns

## 🔄 When to Use Strategy Pattern?

Use the Strategy pattern when:

✅ You have multiple ways of performing a task  
✅ You want to switch algorithms at runtime  
✅ You want to avoid complex conditional statements  
✅ You need to support different variants of an algorithm  
✅ You want to make your code more maintainable and extensible  

## ⚠️ When NOT to Use?

Avoid the Strategy pattern when:

❌ You only have one way of doing something  
❌ The strategies are very simple and unlikely to change  
❌ The overhead of creating multiple classes isn't justified  
❌ The algorithm selection logic is very complex  

## 🔗 Related Patterns

- **State Pattern**: Similar structure but strategies represent states
- **Template Method**: Defines algorithm skeleton, strategies define steps
- **Factory Pattern**: Can be used to create strategy instances
- **Command Pattern**: Encapsulates requests, strategies encapsulate algorithms

## 📚 Key Takeaways

1. **Strategy Pattern** = Family of interchangeable algorithms
2. **Context** remains independent of specific strategy implementations  
3. **Runtime flexibility** without modifying existing code
4. **Promotes** composition over inheritance
5. **Follows** SOLID principles, especially Open/Closed

The Strategy Pattern is a powerful tool for creating flexible, maintainable code that can adapt to changing requirements without breaking existing functionality. It's particularly useful in scenarios where you need to support multiple algorithms or behaviors that can be selected at runtime.