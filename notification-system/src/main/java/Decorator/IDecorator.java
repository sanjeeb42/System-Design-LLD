package Decorator;

import Notifications.INotification;

import javax.management.Notification;

abstract class IDecorator implements INotification {
    public INotification notification;

    public IDecorator(INotification iNotification){
        this.notification=iNotification;
    }
}
