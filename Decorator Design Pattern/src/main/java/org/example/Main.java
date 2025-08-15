package org.example;
import Character.*;
import Decorator.GunPowerUp;
import Decorator.HeightUp;

public class Main {
    public static void main(String[] args) {

        System.out.println("Example of Decorator Design Pattern");

        // Create a normal character
        character character=new Mario();

        // Decorate GunPower abilities
        character=new GunPowerUp(character);
        System.out.println(character.getAbilities());

        // Add height Up abilities as well
        character=new HeightUp(character);
        System.out.println(character.getAbilities());
    }
}