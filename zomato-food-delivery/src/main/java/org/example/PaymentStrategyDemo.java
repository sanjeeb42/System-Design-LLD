package org.example;

import models.*;
import strategies.*;

import java.util.List;

/**
 * Demonstration of Strategy Design Pattern using Payment Strategies
 * This class shows how different payment methods (UPI, Credit Card, Net Banking)
 * can be implemented using the Strategy pattern
 */
public class PaymentStrategyDemo {
    
    public static void main(String[] args) {
        // Create Zomato Object
        ZomatoApp zomato = new ZomatoApp();
        zomato.initializeRestaurants();
        
        // Create a user
        User user = new User(101, "Sanjeeb", "Delhi");
        System.out.println("=== Strategy Design Pattern Demo ===");
        System.out.println("User: " + user.getName() + " is ordering food.");
        System.out.println();
        
        // Search and select restaurant
        List<Restaurant> restaurantList = zomato.searchRestaurants("Delhi");
        if (restaurantList.isEmpty()) {
            System.out.println("No restaurants found!");
            return;
        }
        
        System.out.println("Selected restaurant: " + restaurantList.get(0).getName());
        System.out.println();
        
        // Demonstrate different payment strategies
        demonstratePaymentStrategies(zomato, user, restaurantList);
    }
    
    private static void demonstratePaymentStrategies(ZomatoApp zomato, User user, List<Restaurant> restaurantList) {
        System.out.println("=== Demonstrating Different Payment Strategies ===");
        System.out.println();
        
        // Strategy 1: UPI Payment
        System.out.println("1. Using UPI Payment Strategy:");
        System.out.println("   - Strategy: UpiPaymentStrategy");
        System.out.println("   - Implementation: Mobile number based payment");
        
        zomato.selectRestaurant(user, restaurantList.get(0));
        zomato.addToCart(user, "P1");
        zomato.addToCart(user, "P2");
        zomato.printUserCart(user);
        
        PaymentStrategy upiStrategy = new UpiPaymentStrategy("9876543210");
        Order upiOrder = zomato.checkoutNow(user, "Delivery", upiStrategy);
        zomato.payForOrder(user, upiOrder);
        System.out.println();
        
        // Strategy 2: Credit Card Payment
        System.out.println("2. Using Credit Card Payment Strategy:");
        System.out.println("   - Strategy: CreditCardPaymentStrategy");
        System.out.println("   - Implementation: Card number based payment");
        
        zomato.selectRestaurant(user, restaurantList.get(0));
        zomato.addToCart(user, "P1");
        System.out.println("Items in cart: P1 : Chole Bhature : ₹120");
        
        PaymentStrategy cardStrategy = new CreditCardPaymentStrategy("**** **** **** 1234");
        Order cardOrder = zomato.checkoutNow(user, "Delivery", cardStrategy);
        zomato.payForOrder(user, cardOrder);
        System.out.println();
        
        // Strategy 3: Net Banking Payment
        System.out.println("3. Using Net Banking Payment Strategy:");
        System.out.println("   - Strategy: NetBankingPaymentStrategy");
        System.out.println("   - Implementation: Bank name and account holder based payment");
        
        zomato.selectRestaurant(user, restaurantList.get(0));
        zomato.addToCart(user, "P2");
        System.out.println("Items in cart: P2 : Samosa : ₹15");
        
        PaymentStrategy netBankingStrategy = new NetBankingPaymentStrategy("HDFC Bank", "Sanjeeb Kumar");
        Order netBankingOrder = zomato.checkoutNow(user, "Delivery", netBankingStrategy);
        zomato.payForOrder(user, netBankingOrder);
        System.out.println();
        
        // Demonstrate strategy switching at runtime
        demonstrateRuntimeStrategySwitching(zomato, user, restaurantList);
    }
    
    private static void demonstrateRuntimeStrategySwitching(ZomatoApp zomato, User user, List<Restaurant> restaurantList) {
        System.out.println("=== Runtime Strategy Switching Demo ===");
        System.out.println("Showing how payment strategy can be changed at runtime...");
        System.out.println();
        
        // Add item for demo
        zomato.selectRestaurant(user, restaurantList.get(0));
        zomato.addToCart(user, "P1");
        
        // Create an order and demonstrate strategy switching
        Order order = zomato.checkoutNow(user, "Delivery", new UpiPaymentStrategy("1111111111"));
        
        System.out.println("Initial payment strategy: UPI");
        System.out.println("Switching to Credit Card...");
        order.setPaymentStrategy(new CreditCardPaymentStrategy("**** **** **** 5678"));
        
        System.out.println("Switching to Net Banking...");
        order.setPaymentStrategy(new NetBankingPaymentStrategy("SBI", "Sanjeeb Kumar"));
        
        // Final payment - process manually to show the strategy switching
        System.out.println("Processing payment with final strategy:");
        order.processPayment(); // Direct call instead of through zomato.payForOrder to avoid clearing cart
        
        System.out.println();
        System.out.println("=== Strategy Pattern Benefits Demonstrated ===");
        System.out.println("✓ Easy to add new payment methods without modifying existing code");
        System.out.println("✓ Can switch payment strategies at runtime");
        System.out.println("✓ Each payment method is encapsulated in its own class");
        System.out.println("✓ Follows Open/Closed Principle - open for extension, closed for modification");
        System.out.println();
        
        // Show all available strategies
        System.out.println("=== Available Payment Strategies ===");
        PaymentStrategy[] strategies = {
            new UpiPaymentStrategy("9876543210"),
            new CreditCardPaymentStrategy("**** **** **** 1234"),
            new NetBankingPaymentStrategy("ICICI Bank", "John Doe")
        };
        
        System.out.println("All payment strategies implement the same PaymentStrategy interface:");
        for (PaymentStrategy strategy : strategies) {
            System.out.println("- " + strategy.getClass().getSimpleName());
            strategy.pay(100.0); // Demo payment of ₹100
        }
    }
}