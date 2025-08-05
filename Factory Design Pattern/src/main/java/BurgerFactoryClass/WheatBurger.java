package BurgerFactoryClass;

import burgerClass.*;

public class WheatBurger implements BurgerFactory{
    @Override
    public Burger createBurger(String type) {
        if (type.equalsIgnoreCase("basic")) return new BasicWheatBurger();
        else if(type.equalsIgnoreCase("standard"))return new StandardWheatBurger();
        else if(type.equalsIgnoreCase("premium"))return new PremiumWheatBurger();
        return null;
    }
}
