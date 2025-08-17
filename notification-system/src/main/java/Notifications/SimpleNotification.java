package Notifications;

public class SimpleNotification implements INotification{

    private String text;

    public SimpleNotification(String text){
        this.text=text;
    }

    @Override
    public String getContent() {
        return text;
    }
}
