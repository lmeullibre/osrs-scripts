package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.grandexchange.Status;
import org.dreambot.api.wrappers.items.Item;

public class BuyItemsNode extends Node {
    private Utils utils;
    private long lastBuyOfferTime;

    public BuyItemsNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return utils.isRunning() && utils.getActiveItem() != null && utils.getActiveItemQuantity() == 0;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int execute() {
        Item activeItem = utils.getActiveItem();
        GrandExchangeItem offer = GrandExchange.getItem(activeItem.getID());

        if (offer != null) {
            Status offerStatus = offer.getStatus();
            if (offerStatus == Status.BUY && System.currentTimeMillis() - lastBuyOfferTime > 60000) {
                GrandExchange.cancelAll();
                GrandExchange.collect();
                utils.nextActiveItem();
                utils.setActiveItemQuantity(0);
                lastBuyOfferTime = 0;
            }
        }

        if (!GrandExchange.isOpen()) {
            GrandExchange.open();
            sleepUntil(() -> GrandExchange.isOpen(), 2000, 1000);
        }

        final int NATURE_RUNE_COST = utils.getNatureRunePrice();
        int maxPrice = activeItem.getHighAlchValue() - NATURE_RUNE_COST;
        int maxQuantity;
        if (utils.getSpentCoins() + utils.getMaxCoinsPerSet() > utils.getMaxCoins()) {
            maxQuantity = (utils.getMaxCoins() - utils.getSpentCoins()) / maxPrice;
        } else {
            maxQuantity = utils.getMaxCoinsPerSet() / maxPrice;
        }

        activeItem = utils.getActiveItem();
        offer = GrandExchange.getItem(activeItem.getID());

        if (offer == null || offer.getStatus() == Status.EMPTY) {
            GrandExchange.buyItem(activeItem.getID( ), maxQuantity, maxPrice);
            Item finalActiveItem = activeItem;
            sleepUntil(() -> GrandExchange.getItem(finalActiveItem.getID()) != null, 10000, 1000);
            offer = GrandExchange.getItem(activeItem.getID());
            utils.addSpentCoins(offer.getPrice() * offer.getAmount());
            lastBuyOfferTime = System.currentTimeMillis();
        }

        Status offerStatus = offer.getStatus();

        if (offerStatus == Status.BUY_COLLECT) {
            int collectedAmount = offer.getAmount();
            GrandExchange.collect();
            utils.increaseItemQuantity(collectedAmount);
            utils.incrementProfit(offer.getValue());
            GrandExchange.close();
            sleepUntil(() -> !GrandExchange.isOpen(), 1000, 3000);
            utils.setRandomMouseSpeed();
            return Calculations.random(1000, 3000);
        } else if (offerStatus == Status.BUY) {
            utils.setRandomMouseSpeed();
            return Calculations.random(1000, 3000);
        } else if (offerStatus == Status.EMPTY) {
            utils.setRandomMouseSpeed();
            return Calculations.random(1000, 3000);
        } else {
            utils.setRandomMouseSpeed();
            return Calculations.random(1000, 3000);
        }
    }
}