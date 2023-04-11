package Slayer;

import org.apache.tools.ant.Task;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.TaskNode;

import java.util.Arrays;

public class DialogEnterBossNode extends TaskNode {

    @Override
    public int priority() {
        return 5;
    }
//
    @Override
    public boolean accept() {

        return (Dialogues.areOptionsAvailable());
    }

    Area bossArea = new Area(3074, 9791, 3107, 9820);

    @Override
    public int execute() {
       if (Dialogues.areOptionsAvailable()){
           log("he entrat al if");
           Dialogues.chooseOption("Yes.");
           sleepUntil(()-> !bossArea.contains(Players.getLocal()), 10000, 1000);
       }
        return Calculations.random(500, 2000);
    }
}
