package RobotClass;

import Flyable.Flyable;
import Talkable.Talkable;
import Walkable.Walkable;

public abstract class Robot {
    protected Walkable walkBehavior;
    protected Talkable talkBehavior;
    protected Flyable flyBehavior;

    // Constructor to initialize behaviors
    public Robot(Walkable walkBehavior, Talkable talkBehavior, Flyable flyBehavior) {
        this.walkBehavior = walkBehavior;
        this.talkBehavior = talkBehavior;
        this.flyBehavior = flyBehavior;
    }

    // Delegate to the strategy objects
    public void walk() {
        walkBehavior.walk();
    }

    public void talk() {
        talkBehavior.talk();
    }

    public void fly() {
        flyBehavior.fly();
    }

    // Abstract method - each robot type will implement this differently
    public abstract void projection();

    // Methods to change behaviors at runtime (Strategy Pattern benefit)
    public void setWalkBehavior(Walkable walkBehavior) {
        this.walkBehavior = walkBehavior;
    }

    public void setTalkBehavior(Talkable talkBehavior) {
        this.talkBehavior = talkBehavior;
    }

    public void setFlyBehavior(Flyable flyBehavior) {
        this.flyBehavior = flyBehavior;
    }
}
