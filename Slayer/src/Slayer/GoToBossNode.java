package Slayer;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.GameObject;

public class GoToBossNode extends TaskNode {

    @Override
    public int priority(){
        return 4;
    }

    Area bossArea = new Area(3145, 9837, 3168, 9815);

    @Override
    public boolean accept() {
        return (!Players.getLocal().isAnimating() && Inventory.contains(20754));
    }

    @Override
    public int execute() {
        GameObject gate = GameObjects.closest("Gate");
        if (gate != null && gate.hasAction("Open") && gate.canReach()){
            gate.interact("Open");
            sleepUntil(()-> Players.getLocal().isAnimating(), 1000, 1000);
            //sleepUntil(()-> bossArea.contains(Players.getLocal()),1000, 1000);
        }
        return 300;
    }
}
