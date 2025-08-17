package Observable;

import Observer.IObserver;

public interface IObservable {
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObserver();
}
