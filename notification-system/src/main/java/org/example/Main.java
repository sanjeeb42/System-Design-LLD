package org.example;

import Decorator.SignatureDecorator;
import Decorator.TimeStampDecorator;
import NotificationStrategy.EmailStrategy;
import NotificationStrategy.SMSStrategy;
import Notifications.INotification;
import Notifications.SimpleNotification;
import Observable.NotificationObservable;
import Observer.Logger;
import Observer.NotificationEngine;
import SingleTonClass.NotificationService;

public class Main {
    public static void main(String[] args) {
        NotificationService notificationService=NotificationService.getInstance();

        NotificationObservable notificationObservable=notificationService.getObservable();
        Logger logger=new Logger(notificationObservable);

        NotificationEngine notificationEngine = new NotificationEngine(notificationObservable);

        notificationEngine.addNotificationStrategy(new EmailStrategy("sanjeeb@gmail.com"));
        notificationEngine.addNotificationStrategy(new SMSStrategy("+91-9954258376"));

        notificationObservable.addObserver(logger);
        notificationObservable.addObserver(notificationEngine);

        INotification notification = new SimpleNotification("Your order has been shipped!");
        notification = new TimeStampDecorator(notification);
        notification = new SignatureDecorator(notification, "Customer Care");

        notificationService.sendNotification(notification);
    }
}