package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.grandexchange.Status;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.ScriptManager;

public class StopNode extends Node {
    private Utils utils;

    public StopNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {

        if (utils.shouldStop){
            return true;
        }

        if (utils.isKilled()) {
            log("Script has been killed. Stopping the script.");
            return true;
        }
        Area area = new Area(3157, 3496, 3172, 3483);
        if (!area.contains(Players.getLocal().getTile())) {
            log("Player not in the Grand Exchange. Stopping the script.");
            return true;
        }

        return false;
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public int execute() {
        ScriptManager.getScriptManager().stop();
        return Calculations.random(1000, 3000);
    }
}