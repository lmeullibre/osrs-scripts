package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.grandexchange.Status;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.wrappers.items.Item;

public class BuyItemsNode extends Node {
    private Utils utils;
    private long lastBuyOfferTime; // Variable to store when the last buy offer was placed

    public BuyItemsNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        if (!utils.isRunning()) return false;
        return utils.getActiveItem() != null && utils.getActiveItemQuantity() == 0;

    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int execute() {

        // Get active item and its ID
        Item activeItem = utils.getActiveItem();

        // Get active offer
        GrandExchangeItem offer = GrandExchange.getItem(activeItem.getID());

        if (offer != null) {
            Status offerStatus = offer.getStatus();

            if (offerStatus == Status.BUY && System.currentTimeMillis() - lastBuyOfferTime > 5000) { // 1 minute timeout
                log("buscant nou item");
                GrandExchange.cancelAll();
                GrandExchange.collect();
                // Update active item to the next most profitable one
                utils.nextActiveItem();
                utils.setActiveItemQuantity(0);
                log("new item");
                log(utils.getActiveItem().getName());
                log(utils.getActiveItem().getName());
                // You may want to reset the last buy offer time here too
                lastBuyOfferTime = 0;
                offer = null;
                //return Calculations.random(1000, 3000);
            }
        }


        // Check if GE interface is open
        if (!GrandExchange.isOpen()) {
            // Open GE interface if it's not open
            GrandExchange.open();
            sleepUntil(() -> GrandExchange.isOpen(), 2000, 1000);
        }

        // Assuming the cost of a nature rune is constant
        final int NATURE_RUNE_COST = LivePrices.get("Nature Rune");

        // Determine maximum price to still profit from high alchemy
        int maxPrice = activeItem.getHighAlchValue() - NATURE_RUNE_COST;

        // Determine the maximum quantity to buy based on the player's available coins
        int playerCoins = Inventory.count("Coins");
        int maxQuantity = playerCoins / maxPrice;

        maxQuantity = Math.min(maxQuantity, utils.getMaxCoins() / maxPrice);
        // If no active offer for the item or offer is cancelled, buy the item at the calculated price and quantity
        // Get active item and its ID
        activeItem = utils.getActiveItem();

        // Get active offer
        offer = GrandExchange.getItem(activeItem.getID());

        if (offer == null || offer.getStatus() == Status.EMPTY) {
            GrandExchange.buyItem(activeItem.getID(), maxQuantity, maxPrice);
            Item finalActiveItem = activeItem;
            sleepUntil(()-> GrandExchange.getItem(finalActiveItem.getID()) != null, 10000, 1000);
            offer = GrandExchange.getItem(activeItem.getID());
            utils.addSpentCoins(offer.getPrice()*offer.getAmount());
            lastBuyOfferTime = System.currentTimeMillis(); // Update the last buy offer time
        }

        Status offerStatus = offer.getStatus();

        if (offerStatus == Status.BUY_COLLECT) {
            int collectedAmount = offer.getAmount();
            GrandExchange.collectToBank();

            utils.increaseItemQuantity(collectedAmount);

            GrandExchange.close();
            sleepUntil(()-> !GrandExchange.isOpen(), 1000, 3000);

            return Calculations.random(1000, 3000);
        } else if (offerStatus == Status.BUY) {
            // If the offer is still in progress, return a longer delay
            return Calculations.random(1000, 3000);
        } else if (offerStatus == Status.EMPTY) {
            // If the offer is empty, return a medium delay
            return Calculations.random(1000, 3000);
        } else {
            // If the offer status is none of the above, return a standard delay
            return Calculations.random(1000, 3000);
        }
    }
}