package Slayer;

import org.dreambot.api.methods.Animations;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;

public class LootNode extends TaskNode {

    @Override
    public int priority(){
        return 2;
    }

    @Override
    public boolean accept() {
        return (!Players.getLocal().isInCombat() &&
                (GroundItems.closest("Big bones") != null || GroundItems.closest(20754) != null)) &&
                GroundItems.closest("Big bones").canReach() || GroundItems.closest(20754).canReach();
    }

    @Override
    public int execute() {

        GroundItem item = GroundItems.closest("Big bones", "Giant key");
        if (item != null){
            item.interact("Take");
            sleepUntil(()-> Players.getLocal().isMoving(), 1000, 1000);
        }
        return Calculations.random(500, 2000);
    }
}
