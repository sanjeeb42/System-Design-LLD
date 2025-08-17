package Observable;

import Notifications.INotification;
import Observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class NotificationObservable implements IObservable{
    List<IObserver>observers=new ArrayList<>();
    private INotification notification;

    @Override
    public void addObserver(IObserver obs) {
        if(!observers.contains(obs))observers.add(obs);
    }

    @Override
    public void removeObserver(IObserver obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObserver() {
        for(IObserver obs:observers){
            obs.update();
        }
    }
     
    public void setNotification(INotification notification){
        this.notification=notification;
        notifyObserver();
    }

    public String getNotification(){
        return notification.getContent();
    }
}
