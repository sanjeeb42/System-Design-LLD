package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    Restaurant restaurant;
    List<MenuItems>items=new ArrayList<>();

    public Cart() {
        this.restaurant = null;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<MenuItems> getItems() {
        return items;
    }

    public void setItems(List<MenuItems> items) {
        this.items = items;
    }

    void addToCart(MenuItems item){
        if (restaurant == null) {
            System.err.println("Cart: Set a restaurant before adding items.");
            return;
        }
        items.add(item);
    }

    public double getTotalCost(){
        double cost=0;
        for(MenuItems item:items){
            cost+=item.getPrice();
        }
        return cost;
    }

    public boolean isEmpty(){
        return restaurant==null || items.isEmpty();
    }

    void clearCart(){
        items.clear();
        restaurant=null;
    }
}
