package Decorator;

import Notifications.INotification;

public class SignatureDecorator extends IDecorator{

    private String signature;
    public SignatureDecorator(INotification iNotification,String signature) {
        super(iNotification);
        this.signature=signature;
    }

    @Override
    public String getContent() {
        return notification.getContent()+ "\n-- "+signature+" \n\n";
    }
}
