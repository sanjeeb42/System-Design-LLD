# Strategy Design Pattern - Payment Strategy Example

This folder demonstrates the **Strategy Design Pattern** using a payment processing system with three different payment methods: UPI, Credit Card, and Net Banking.

## 📁 Structure

```
Strategy Design Pattern/
├── README.md (this file)
├── Flyable/
├── RobotClass/
├── Talkable/
├── Walkable/
├── StrategyDesignPatternClass.java
├── Standard_UML.png
└── Uml_Diagram.png
```

## 🎯 Payment Strategy Implementation

The payment strategy implementation is located in the `zomato-food-delivery` project:

### Key Files:
- `strategies/PaymentStrategy.java` - Interface defining the payment contract
- `strategies/UpiPaymentStrategy.java` - UPI payment implementation
- `strategies/CreditCardPaymentStrategy.java` - Credit card payment implementation  
- `strategies/NetBankingPaymentStrategy.java` - Net banking payment implementation
- `org/example/SimplePaymentDemo.java` - Focused demonstration of the pattern

## 🚀 Running the Examples

Navigate to the zomato-food-delivery directory and run:

```bash
cd ../zomato-food-delivery

# Compile the project
mvn compile

# Run the simple payment strategy demo (recommended)
mvn exec:java -Dexec.mainClass="org.example.SimplePaymentDemo"

# Run the original Zomato app with UPI payment
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## 💡 Strategy Pattern Components

### 1. Strategy Interface
```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

### 2. Concrete Strategies
- **UpiPaymentStrategy**: Handles UPI payments with mobile number
- **CreditCardPaymentStrategy**: Handles card payments with card number
- **NetBankingPaymentStrategy**: Handles net banking with bank name and account holder

### 3. Context (Order/PaymentContext)
Uses the PaymentStrategy interface to process payments without knowing the specific implementation.

## 📋 Sample Output

```
=== Strategy Design Pattern Demo ===
Payment processing using different strategies

Order Total: ₹1250.0

1. UPI Payment Strategy:
Processing payment using: UpiPaymentStrategy
Paid ₹1250.0 using UPI (9876543210)

2. Credit Card Payment Strategy:
Processing payment using: CreditCardPaymentStrategy
Paid ₹1250.0 using Credit Card (**** **** **** 1234)

3. Net Banking Payment Strategy:
Processing payment using: NetBankingPaymentStrategy
Paid ₹1250.0 using Net Banking (HDFC Bank - Sanjeeb Kumar)

=== Runtime Strategy Switching Demo ===
Initial strategy: UPI
Paid ₹1250.0 using UPI (1111111111)

Switching to: Credit Card
Paid ₹1250.0 using Credit Card (**** **** **** 5678)

Switching to: Net Banking
Paid ₹1250.0 using Net Banking (SBI - Sanjeeb Kumar)
```

## ✅ Key Benefits Demonstrated

1. **Open/Closed Principle**: Easy to add new payment methods without modifying existing code
2. **Runtime Flexibility**: Payment strategies can be switched at runtime
3. **Single Responsibility**: Each payment method is encapsulated in its own class
4. **Code Reusability**: Strategies can be reused across different contexts
5. **Easy Testing**: Each strategy can be tested independently

## 🔄 Adding New Payment Methods

To add a new payment method (e.g., Digital Wallet):

1. Create a new class implementing `PaymentStrategy`:
```java
public class WalletPaymentStrategy implements PaymentStrategy {
    private String walletId;
    
    public WalletPaymentStrategy(String walletId) {
        this.walletId = walletId;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Digital Wallet (" + walletId + ")");
    }
}
```

2. Use it like any other strategy:
```java
PaymentStrategy wallet = new WalletPaymentStrategy("wallet123");
order.setPaymentStrategy(wallet);
order.processPayment();
```

## 🌍 Real-World Applications

This payment strategy pattern is commonly used in:
- E-commerce platforms (Amazon, Flipkart)
- Food delivery apps (Zomato, Swiggy)
- Payment gateways (Razorpay, Paytm)
- Banking applications
- Subscription services

The pattern allows these applications to support multiple payment methods while keeping the code maintainable and extensible.