package Observer;

import NotificationStrategy.INotificationStrategy;
import Observable.NotificationObservable;

import java.util.ArrayList;
import java.util.List;

public class NotificationEngine implements IObserver {

    List<INotificationStrategy> strategyList=new ArrayList<>();
    public NotificationObservable notification;

    public void addStrategy(INotificationStrategy strategy){
        strategyList.add(strategy);
    }

    @Override
    public void update() {
        for(INotificationStrategy strategy:strategyList){
            strategy.sendNotification(notification.getNotification());
        }
    }
}
