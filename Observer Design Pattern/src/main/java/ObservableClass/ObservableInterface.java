package ObservableClass;

import ObserverClass.ObserverInterface;

public interface ObservableInterface {
    void addsubscribers(ObserverInterface subsrciber);
    void removesubscribers(ObserverInterface subscriber);
    void notifysuscribers();
}
