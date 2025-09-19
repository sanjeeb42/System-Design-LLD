package org.example;

import strategies.*;

/**
 * Simple demonstration of Strategy Design Pattern using Payment Strategies
 * This focuses purely on the Strategy pattern without the complexity of the cart system
 */
public class SimplePaymentDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Strategy Design Pattern Demo ===");
        System.out.println("Payment processing using different strategies");
        System.out.println();
        
        // Sample order amount
        double orderAmount = 1250.0;
        
        // Demonstrate different payment strategies
        System.out.println("Order Total: ₹" + orderAmount);
        System.out.println();
        
        // Strategy 1: UPI Payment
        System.out.println("1. UPI Payment Strategy:");
        PaymentStrategy upiStrategy = new UpiPaymentStrategy("9876543210");
        processPayment(upiStrategy, orderAmount);
        System.out.println();
        
        // Strategy 2: Credit Card Payment
        System.out.println("2. Credit Card Payment Strategy:");
        PaymentStrategy cardStrategy = new CreditCardPaymentStrategy("**** **** **** 1234");
        processPayment(cardStrategy, orderAmount);
        System.out.println();
        
        // Strategy 3: Net Banking Payment
        System.out.println("3. Net Banking Payment Strategy:");
        PaymentStrategy netBankingStrategy = new NetBankingPaymentStrategy("HDFC Bank", "Sanjeeb Kumar");
        processPayment(netBankingStrategy, orderAmount);
        System.out.println();
        
        // Demonstrate strategy switching at runtime
        demonstrateRuntimeStrategySwitching(orderAmount);
        
        // Show pattern benefits
        demonstratePatternBenefits();
    }
    
    private static void processPayment(PaymentStrategy strategy, double amount) {
        System.out.println("Processing payment using: " + strategy.getClass().getSimpleName());
        strategy.pay(amount);
    }
    
    private static void demonstrateRuntimeStrategySwitching(double amount) {
        System.out.println("=== Runtime Strategy Switching Demo ===");
        System.out.println("Creating a payment context and switching strategies dynamically...");
        System.out.println();
        
        // Create a payment context (similar to Order class)
        PaymentContext paymentContext = new PaymentContext(amount);
        
        // Strategy 1
        System.out.println("Initial strategy: UPI");
        paymentContext.setPaymentStrategy(new UpiPaymentStrategy("1111111111"));
        paymentContext.executePayment();
        
        // Switch to Strategy 2
        System.out.println("\nSwitching to: Credit Card");
        paymentContext.setPaymentStrategy(new CreditCardPaymentStrategy("**** **** **** 5678"));
        paymentContext.executePayment();
        
        // Switch to Strategy 3
        System.out.println("\nSwitching to: Net Banking");
        paymentContext.setPaymentStrategy(new NetBankingPaymentStrategy("SBI", "Sanjeeb Kumar"));
        paymentContext.executePayment();
        
        System.out.println();
    }
    
    private static void demonstratePatternBenefits() {
        System.out.println("=== Strategy Pattern Benefits ===");
        System.out.println("✓ Open/Closed Principle: Easy to add new payment methods");
        System.out.println("✓ Single Responsibility: Each strategy handles one payment method");
        System.out.println("✓ Runtime Flexibility: Switch strategies without changing code");
        System.out.println("✓ Code Reusability: Strategies can be reused across contexts");
        System.out.println("✓ Easy Testing: Each strategy can be tested independently");
        System.out.println();
        
        System.out.println("=== Adding a New Strategy (Example) ===");
        System.out.println("To add a new payment method like 'Wallet Payment':");
        System.out.println("1. Create WalletPaymentStrategy class implementing PaymentStrategy");
        System.out.println("2. No need to modify existing code");
        System.out.println("3. Use it like any other strategy");
        System.out.println();
        
        // Example of how easy it is to add a new strategy
        PaymentStrategy walletStrategy = new PaymentStrategy() {
            @Override
            public void pay(double amount) {
                System.out.println("Paid ₹" + amount + " using Digital Wallet");
            }
        };
        
        System.out.println("New strategy in action:");
        processPayment(walletStrategy, 500.0);
    }
    
    // Helper class to demonstrate the context pattern
    static class PaymentContext {
        private PaymentStrategy paymentStrategy;
        private double amount;
        
        public PaymentContext(double amount) {
            this.amount = amount;
        }
        
        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.paymentStrategy = strategy;
        }
        
        public void executePayment() {
            if (paymentStrategy != null) {
                paymentStrategy.pay(amount);
            } else {
                System.out.println("No payment strategy set!");
            }
        }
    }
}