package org.example;

import BurgerFactoryClass.BurgerFactory;
import BurgerFactoryClass.WheatBurger;
import burgerClass.Burger;

public class FactoryMethod {
    public static void main(String[] args) {
        String type="premium";
        BurgerFactory burgerFactory=new WheatBurger();
        Burger burger= burgerFactory.createBurger(type);
        if(burger!=null)burger.prepare();
    }
}