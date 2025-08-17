package Decorator;

import Notifications.INotification;

public class TimeStampDecorator extends IDecorator {
    public TimeStampDecorator(INotification iNotification) {
        super(iNotification);
    }

    @Override
    public String getContent() {
        return "[2025-04-13 14:22:00] "+ notification.getContent();
    }
}
