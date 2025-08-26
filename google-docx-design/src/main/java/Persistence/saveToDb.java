package Persistence;

public class saveToDb implements IPersistence{

    @Override
    public void save(String data) {
        System.out.println("Saving data to db successfull");
    }
}
