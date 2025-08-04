package RobotClass;

import Flyable.Flyable;
import Talkable.Talkable;
import Walkable.Walkable;

public class SpecialRobot extends Robot {

    public SpecialRobot(Walkable walkBehavior, Talkable talkBehavior, Flyable flyBehavior) {
        super(walkBehavior, talkBehavior, flyBehavior);
    }

    @Override
    public void projection() {
        System.out.println(" I am a Special Robot with unique capabilities and advanced features!");
    }
}
