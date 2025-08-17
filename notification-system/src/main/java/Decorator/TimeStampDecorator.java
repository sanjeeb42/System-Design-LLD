package Decorator;

import Notifications.INotification;

public class TimeStampDecorator extends IDecorator {
    public TimeStampDecorator(INotification iNotification) {
        super(iNotification);
    }

    @Override
    public String getContent() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "[" + timestamp + "] " + notification.getContent();
    }
}
