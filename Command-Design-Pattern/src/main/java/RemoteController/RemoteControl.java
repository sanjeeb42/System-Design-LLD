package RemoteController;

import Command.ICommand;

import java.util.ArrayList;
import java.util.List;

public class RemoteControl {
    private static final int MAX_COMMANDS = 4;

    List<ICommand>commands;
    List<Boolean>isTurnedon;

    public RemoteControl(){

        commands=new ArrayList<>(MAX_COMMANDS);
        isTurnedon=new ArrayList<>(MAX_COMMANDS);

        for(int i=0;i<4;i++){
            commands.add(null);
            isTurnedon.add(false);
        }
    }

    public void assignCommands(int index,ICommand command){
        if(index>=0 && index<MAX_COMMANDS){

            commands.set(index,command);
            isTurnedon.set(index,false);
        }
    }

    public void pressButton(int index){
        if(index<0 ||index>=MAX_COMMANDS){
            System.out.println("Index outside of Range , Please check again");
            return;
        }
        ICommand command = commands.get(index);
        if (command == null) {
            // Optionally, log or handle the case where no command is assigned
            return;
        }

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
