package Decorator;
import Character.character;

abstract class Decorator implements character {
    public character character;

    public Decorator(character character){
        this.character = character;
    }
}
