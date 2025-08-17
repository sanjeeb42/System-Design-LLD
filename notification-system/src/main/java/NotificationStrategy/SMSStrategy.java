package NotificationStrategy;

public class SMSStrategy implements INotificationStrategy{
    private String mobileNumber;

    public SMSStrategy(String mobileNumber){
        this.mobileNumber=mobileNumber;
    }

    @Override
    public void sendNotification(String content) {
        System.out.println("SMS sent to mobile number: "+mobileNumber+ "\n"+ content);
    }
}
