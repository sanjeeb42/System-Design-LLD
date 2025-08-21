package org.example;

import models.*;
import strategies.*;

import java.util.List;

public class Main{
    public static void main(String[] args) {
        // Create Zomato Object
        ZomatoApp zomato = new ZomatoApp();

        // Initialize restaurants first
        zomato.initializeRestaurants();

        // Simulate a user coming in (Happy Flow)
        User user = new User(101, "Sanjeeb", "Delhi");
        System.out.println("User: " + user.getName() + " is active.");

        List<Restaurant> restaurantList = zomato.searchRestaurants("Delhi");

        if (restaurantList.isEmpty()) {
            System.out.println("No restaurants found!");
            return;
        }

        System.out.println("Found Restaurants:");
        for (Restaurant restaurant : restaurantList) {
            System.out.println(" - " + restaurant.getName());
        }

        // User selects a restaurant
        zomato.selectRestaurant(user, restaurantList.get(0));
        System.out.println("Selected restaurant: " + restaurantList.get(0).getName());

        // User adds items to the cart
        zomato.addToCart(user, "P1");
        zomato.addToCart(user, "P2");

        zomato.printUserCart(user);

        // User checkout the cart
        Order order = zomato.checkoutNow(user, "Delivery", new UpiPaymentStrategy("1234567890"));

        // User pays for the cart. If payment is successful, notification is sent.
        zomato.payForOrder(user, order);
    }
}