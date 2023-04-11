package Slayer;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.TaskNode;

public class BuryNode extends TaskNode {

    @Override
    public int priority(){
        return 4;
    }

    @Override
    public boolean accept() {
        return (!Players.getLocal().isInCombat() && Inventory.contains(532));
    }

    @Override
    public int execute() {
        Inventory.get(532).interact("Bury");
        sleepUntil(()-> Players.getLocal().isAnimating(), 1000, 1000);
        return 0;
    }
}
