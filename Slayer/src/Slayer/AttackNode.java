package Slayer;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Shop;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.NPC;

public class AttackNode extends TaskNode {

    Area combatZone = new Area(3092, 9842, 3109, 9820);

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean accept() {
        return true;
    }

    @Override
    public int execute() {
        log("entrant al execute");
        if (NPCs.closest("Hill Giant") != null && combatZone.contains(NPCs.closest("Hill Giant"))) {
            NPC giant = NPCs.closest("Hill Giant");
            if (!giant.isInCombat()) {
                log(giant.getName());
                giant.interact("Attack");
                sleepUntil(() -> Players.getLocal().isAnimating(), 1000, 1000);
                log("primer itmout");
                sleepUntil(() -> (!giant.exists() || Players.getLocal().getHealthPercent() < 50), 30000, 2000);
                log("timeout acabat de la mort");
            }
        }
        return 300;
    }
}
