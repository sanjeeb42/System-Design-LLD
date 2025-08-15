package ObservableClass;

import ObserverClass.ObserverConcreteClass;
import ObserverClass.ObserverInterface;

import java.util.ArrayList;
import java.util.List;

public class ObservableConcreteClass implements ObservableInterface {

    List<ObserverInterface> subscribers;
    private String latestVideo;
    private String channelName;

    public ObservableConcreteClass(String name){
        this.channelName=name;
        subscribers=new ArrayList<>();
    }

    @Override
    public void addsubscribers(ObserverInterface subscriber) {
        if(!subscribers.contains(subscriber))subscribers.add(subscriber);
    }

    @Override
    public void removesubscribers(ObserverInterface subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifysuscribers() {
        for(ObserverInterface subscriber:subscribers){
            subscriber.update();
        }
    }

    public void uploadVideo(String title){
        latestVideo=title;
        System.out.println(channelName +" uploaded a new video with title: "+latestVideo);
        notifysuscribers();
    }

    public String getLatestVideo(){
        return latestVideo;
    }
}
