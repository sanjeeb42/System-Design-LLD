package InterfaceSegregationPrinciple;

interface Waiter {
    void serveFood();
    void takeOrder();
}

interface Cook {
    void cookFood();
}

interface Cleaner {
    void washDishes();
}

// Concrete classes implement only what they need, bekar kuch nahi
class RestaurantWaiter implements Waiter {
    @Override
    public void serveFood() {
        System.out.println("Waiter serving food to customers.");
    }

    @Override
    public void takeOrder() {
        System.out.println("Waiter taking customer's order.");
    }
}

class RestaurantCook implements Cook {
    @Override
    public void cookFood() {
        System.out.println("Cook preparing food in the kitchen.");
    }
}

class Dishwasher implements Cleaner {
    @Override
    public void washDishes() {
        System.out.println("Dishwasher cleaning the plates.");
    }
}

public class InterfaceSeggregationPrinciple {
    public static void main(String[] args) {
        Waiter waiter = new RestaurantWaiter();
        waiter.takeOrder();
        waiter.serveFood();

        Cook cook = new RestaurantCook();
        cook.cookFood();

        Cleaner cleaner = new Dishwasher();
        cleaner.washDishes();
    }
}
