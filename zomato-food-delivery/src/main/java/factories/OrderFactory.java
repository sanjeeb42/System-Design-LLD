package factories;

import models.*;
import strategies.PaymentStrategy;

import java.util.List;

public interface OrderFactory {
    Order createOrder(User user, Cart cart, Restaurant restaurant, List<MenuItems> menuItems,
                      PaymentStrategy paymentStrategy, double totalCost, String orderType);
}
