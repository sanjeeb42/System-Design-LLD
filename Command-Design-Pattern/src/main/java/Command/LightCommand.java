package Command;

import Receivers.Light;

public class LightCommand implements ICommand{
    Light light;
    public LightCommand(Light light){
        this.light=light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}
