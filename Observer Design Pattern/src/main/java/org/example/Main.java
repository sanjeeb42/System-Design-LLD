package org.example;

import ObservableClass.ObservableConcreteClass;
import ObserverClass.ObserverConcreteClass;

public class Main {
    public static void main(String[] args) {
        System.out.println("Example of Observer Design Pattern");
        ObservableConcreteClass channel=new ObservableConcreteClass("Techwiser");
        ObserverConcreteClass suscriber1=new ObserverConcreteClass("Sanjeeb",channel);
        ObserverConcreteClass suscriber2=new ObserverConcreteClass("Shibam",channel);

        channel.addsubscribers(suscriber1);
        channel.addsubscribers(suscriber2);

        channel.uploadVideo("Why Piracy is back in 2025");

        channel.removesubscribers(suscriber2);
        channel.uploadVideo("How Nothing Phone 3 will Fail in India");

    }
}