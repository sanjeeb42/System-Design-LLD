import Flyable.NoFly;
import Flyable.NormalFly;
import RobotClass.CompanionRobot;
import RobotClass.Robot;
import RobotClass.SpecialRobot;
import Talkable.NoTalk;
import Talkable.NormalTalk;
import Walkable.NormalWalk;

public class StrategyDesignPatternClass {
    public static void main(String[] args) {
        // Create a companion robot with normal walk, normal talk, and no fly abilities
        Robot robot1 = new CompanionRobot(new NormalWalk(), new NormalTalk(), new NoFly());

        System.out.println("===== Robot 1 (Companion Robot) =====");
        robot1.walk();
        robot1.talk();
        robot1.fly();
        robot1.projection();

        System.out.println("\n===== Robot 2 (Special Robot) =====");

        // Create a special robot with normal walk, no talk, and normal fly abilities
        Robot robot2 = new SpecialRobot(new NormalWalk(), new NoTalk(), new NormalFly());
        robot2.walk();
        robot2.talk();
        robot2.fly();
        robot2.projection();
    }
}
