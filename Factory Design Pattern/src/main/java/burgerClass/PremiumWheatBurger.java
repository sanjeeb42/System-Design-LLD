package burgerClass;

public class PremiumWheatBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Hello I am a premium Wheat burger");
    }
}
