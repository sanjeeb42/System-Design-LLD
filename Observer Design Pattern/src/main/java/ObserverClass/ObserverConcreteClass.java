package ObserverClass;

import ObservableClass.ObservableConcreteClass;

public class ObserverConcreteClass implements ObserverInterface{

    private String name;
    private ObservableConcreteClass channel;

    public ObserverConcreteClass(String name, ObservableConcreteClass channel){
        this.name=name;
        this.channel= channel;
    }

    @Override
    public void update() {
        System.out.println("Hey " + name + ", "+ channel.getLatestVideo());
    }
}
