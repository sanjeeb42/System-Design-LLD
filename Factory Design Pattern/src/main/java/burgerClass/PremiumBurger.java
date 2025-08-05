package burgerClass;

public class PremiumBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Hello, I am a Premium Burger");
    }
}
