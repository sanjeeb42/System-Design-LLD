package NotificationStrategy;

public class EmailStrategy implements INotificationStrategy{

    private String emailId;

    public EmailStrategy(String emailId){
        this.emailId=emailId;
    }

    @Override
    public void sendNotification(String content) {
        System.out.println("Email sent to "+emailId+ "\n"+content);
    }
}
