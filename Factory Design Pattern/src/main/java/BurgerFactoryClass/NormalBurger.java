package BurgerFactoryClass;

import burgerClass.Basicburger;
import burgerClass.Burger;
import burgerClass.PremiumBurger;
import burgerClass.StandardBurger;

public class NormalBurger implements BurgerFactory{
    @Override
    public Burger createBurger(String type) {
        if(type.equalsIgnoreCase("basic"))return new Basicburger();
        else if(type.equalsIgnoreCase("standard"))return new StandardBurger();
        else if(type.equalsIgnoreCase("premium"))return new PremiumBurger();
        else return null;
    }
}
