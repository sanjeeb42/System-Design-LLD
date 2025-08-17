package Command;

import Receivers.Fan;

public class FanCommand implements ICommand{
    Fan fan;
    public FanCommand(Fan fan){
        this.fan=fan;
    }

    @Override
    public void execute() {
        fan.on();
    }

    @Override
    public void undo() {
        fan.off();
    }
}
