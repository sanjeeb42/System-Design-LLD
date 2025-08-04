package RobotClass;

import Flyable.Flyable;
import Talkable.Talkable;
import Walkable.Walkable;

public class CompanionRobot extends Robot {

    public CompanionRobot(Walkable walkBehavior, Talkable talkBehavior, Flyable flyBehavior) {
        super(walkBehavior, talkBehavior, flyBehavior);
    }

    @Override
    public void projection() {
        System.out.println("Hello! I am a Companion Robot, here to assist and be your friend!");
    }
}
