package SingleTonClass;

import Notifications.INotification;
import Observable.NotificationObservable;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private NotificationObservable observable;
    private List<INotification> notificationHistory=new ArrayList<>();

    private static NotificationService instance;

    private NotificationService() {
        observable = new NotificationObservable();
    }

    public static NotificationService getInstance(){
        if(instance==null){
            instance=new NotificationService();
        }
        return instance;
    }

    public NotificationObservable getObservable(){
        return observable;
    }

    public void sendNotification(INotification notification){
        notificationHistory.add(notification);
        observable.setNotification(notification);
    }


}
