package RemoteController;

import Command.ICommand;

import java.util.ArrayList;
import java.util.List;

public class RemoteControl {
    List<ICommand>commands;
    List<Boolean>isTurnedon;

    public RemoteControl(){
        commands=new ArrayList<>(4);
        isTurnedon=new ArrayList<>(4);
        for(int i=0;i<4;i++){
            commands.add(null);
            isTurnedon.add(false);
        }
    }

    public void assignCommands(int index,ICommand command){
        if(index>=0 && index<4){
            commands.set(index,command);
            isTurnedon.set(index,false);
        }
    }

    public void pressButton(int index){
        if(isTurnedon.get(index)){
            commands.get(index).undo();
            isTurnedon.set(index,false);
        }
        else {
            commands.get(index).execute();
            isTurnedon.set(index,true);
        }
    }
}
