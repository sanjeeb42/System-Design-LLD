package Decorator;
import Character.character;

public class HeightUp extends Decorator {

    public HeightUp(character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        String ans= character.getAbilities();
        return ans+" and HeightUp abilities ";
    }
}
