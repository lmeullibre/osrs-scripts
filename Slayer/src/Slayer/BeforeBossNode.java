package Slayer;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.GameObject;

public class BeforeBossNode extends TaskNode {

    Area bossArea = new Area(3074, 9791, 3107, 9820);

    @Override
    public int priority() {
        return 5;
    }

    @Override
    public boolean accept() {
        if (GameObjects.closest("Rocks") != null && GameObjects.closest("Rocks").hasAction("Climb")) {
            log("almenys antrae");
            if ((NPCs.closest("Obor") != null)) {
                log( Players.getLocal().canReach(NPCs.closest("Obor").getTile()));
                if (Players.getLocal().canReach(NPCs.closest("Obor").getTile())) return false;
            } else return true;
        }
        return false;
    }

    @Override
    public int execute() {
        if (GameObjects.closest("Rocks") != null) {
            GameObject rocks = GameObjects.closest("Rocks");
            if (rocks.hasAction("Climb")) {
                rocks.interact("Climb");
                sleepUntil(() -> Players.getLocal().isMoving(), 2000, 1000);
            }

        }
        return Calculations.random(300, 2000);
    }
}
