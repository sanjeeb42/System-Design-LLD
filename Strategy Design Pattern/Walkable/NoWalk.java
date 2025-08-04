package Walkable;

public class NoWalk implements Walkable {
    @Override
    public void walk() {
        System.out.println("I cannot walk...");
    }
}
