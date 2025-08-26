package Persistence;

public class printToConsosle implements IPersistence{
    @Override
    public void save(String data) {
        System.out.println("Printing to console - data is: "+data);
    }
}
