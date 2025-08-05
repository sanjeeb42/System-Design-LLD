package burgerClass;

public class StandardBurger implements Burger{
    @Override
    public void prepare() {
        System.out.println("Hello I am a standard Burger");
    }
}
