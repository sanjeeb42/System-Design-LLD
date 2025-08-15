package Decorator;
import Character.character;

public class GunPowerUp extends Decorator {

    public GunPowerUp(character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        String ans= character.getAbilities();
        return ans+" and GunPower abilites";
    }
}
