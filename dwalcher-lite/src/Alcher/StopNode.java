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
        if (utils.isKilled()) {
            log("Script has been killed. Stopping the script.");
            return true;
        }
        Area area = new Area(3157, 3496, 3172, 3483);
        if (!area.contains(Players.getLocal().getTile())) {
            log("Player not in the Grand Exchange. Stopping the script.");
            return true;
        }
        if (!Magic.canCast(Normal.HIGH_LEVEL_ALCHEMY)) {
            log("You can't cast High Alch. Please, check again you have nature runes, you have enough level, and you are wearing a fire staff");
            return true;
        }
        if (utils.getActiveItem() == null) return false;
        boolean hasNatureRunes = Inventory.contains("Nature Rune");
        if (!hasNatureRunes) {
            log("No more Nature Runes left. Stopping the script.");
            return true;
        }

        int itemPrice = LivePrices.get(utils.getActiveItem().getName());
        int remainingCoins = utils.getMaxCoins() - utils.getSpentCoins();

        GrandExchangeItem activeOffer = GrandExchange.getItem(utils.getActiveItem().getID());

        // Check if there is an active offer for the item
        boolean isOfferActive = activeOffer != null && (activeOffer.getStatus() == Status.BUY || activeOffer.getStatus() == Status.BUY_COLLECT);

        if ((remainingCoins < itemPrice) && !isOfferActive && utils.getActiveItemQuantity() == 0) {
            log("No more money or active offer for the item or item quantity is zero. Stopping the script.");
            return true;
        }
        return false;
    }

    @Override
    public int priority() {
        // This node should have the highest priority
        return 3;
    }

    @Override
    public int execute() {
        ScriptManager.getScriptManager().stop();
        // Returning delay is irrelevant here, but we can return a standard delay
        return Calculations.random(1000, 3000);
    }
}