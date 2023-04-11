package Slayer;

import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.NPC;

public class AttackBossNode extends TaskNode {

    @Override
    public int priority() {
        return 5;
    }

    Area bossArea = new Area(3074, 9791, 3107, 9820);

    @Override
    public boolean accept() {
        return bossArea.contains(Players.getLocal()) && NPCs.closest("Obor") != null && Players.getLocal().canReach(NPCs.closest("Obor").getTile());
    }

    @Override
    public int execute() {
        if (NPCs.closest("Obor") != null){
            NPC obor = NPCs.closest("Obor");
            obor.interact("Attack");
            sleepUntil(() -> Players.getLocal().isAnimating(), 1000, 1000);
            sleepUntil(() -> (!obor.exists() || Players.getLocal().getHealthPercent() < 50), 30000, 2000);

        }
        return 300;
    }
}
