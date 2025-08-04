package Talkable;

public class NoTalk implements Talkable {
    @Override
    public void talk() {
        System.out.println("I cannot speak...");
    }
}
