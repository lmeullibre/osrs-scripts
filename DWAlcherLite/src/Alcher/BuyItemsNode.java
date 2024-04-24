package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.grandexchange.Status;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import java.util.Random;

public class BuyItemsNode extends Node {
    private Utils utils;
    private long lastBuyOfferTime;

    private long lastUpdateItemTime = 0;
    private static final long UPDATE_INTERVAL = 900000; // 1 hour in milliseconds

    public BuyItemsNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return utils.isRunning() && utils.isReady();
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int execute() {
        log("Buying");
        utils.setStatus("Buying");
        Random random = new Random();

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateItemTime > UPDATE_INTERVAL) {
            utils.loadProfitableItems();
            lastUpdateItemTime = currentTime;
            log("Updated the list of profitable items.");
        }

        Item activeItem = utils.getActiveItem();
        GrandExchangeItem offer;

        if (!GrandExchange.isOpen()) {
            GrandExchange.open();
            sleepUntil(() -> GrandExchange.isOpen(), 2000, 1000);
            if (!GrandExchange.isOpen()){
                return Calculations.random(1000, 3000);
            }
        }

        final int NATURE_RUNE_COST = utils.getNatureRunePrice();
        int maxPrice = activeItem.getHighAlchValue() - NATURE_RUNE_COST;
        int maxQuantity;

        int globalBudgetLeft = utils.getMaxCoins() - utils.getSpentCoins();
        int setBudgetLeft = utils.getMaxCoinsPerSet();
        int availableBudget = Math.min(globalBudgetLeft, setBudgetLeft);
        if (availableBudget > 0) {
            maxQuantity = availableBudget / maxPrice;
            int adjustment = random.nextInt(6) - 5;

            // Adjust maxQuantity
            maxQuantity += adjustment;
        } else {
            log("Budget exhausted");
            ScriptManager.getScriptManager().stop();
            return Calculations.random(1000, 3000);
        }

        if (maxQuantity > 0) {
            GrandExchange.buyItem(activeItem.getID(), maxQuantity, maxPrice);
            Item finalActiveItem = activeItem;
            sleepUntil(() -> GrandExchange.getItem(finalActiveItem.getID()) != null, 10000, 1000);

            offer = GrandExchange.getItem(activeItem.getID());
            if (offer != null) {
                utils.addSpentCoins(offer.getValue());
            }
            lastBuyOfferTime = System.currentTimeMillis();
        } else {
            ScriptManager.getScriptManager().stop();
            log("Not enough budget left to make a purchase");
            return Calculations.random(1000, 3000);
        }

        if (maxQuantity <= 0) {
            ScriptManager.getScriptManager().stop();
            log("All the money was spent");
            return Calculations.random(1000, 3000);
        }

        activeItem = utils.getActiveItem();
        offer = GrandExchange.getItem(activeItem.getID());


        if (offer == null || offer.getStatus() == Status.EMPTY) {

            GrandExchange.buyItem(activeItem.getID(), maxQuantity, maxPrice);
            Item finalActiveItem = activeItem;
            sleepUntil(() -> GrandExchange.getItem(finalActiveItem.getID()) != null, 10000, 1000);
            offer = GrandExchange.getItem(activeItem.getID());

            utils.addSpentCoins(offer.getValue());
            lastBuyOfferTime = System.currentTimeMillis();
        }

        Status offerStatus = offer.getStatus();
        if (offerStatus == null) {
            int attempts = 0;
            while (offerStatus == null && attempts < 5) {
                sleep(1000);
                offer = GrandExchange.getItem(activeItem.getID());
                if (offer != null) {
                    offerStatus = offer.getStatus();
                }
                attempts++;
            }
        }

        if (offerStatus == null) {
            ScriptManager.getScriptManager().stop();
            log("There was a major error getting the offer, please contact the bot author");
            return Calculations.random(1000, 3000);
        }

        if (offer.getStatus() == Status.BUY_COLLECT) {
            int collectedAmount = offer.getAmount();
            int coinsBefore = 0;
            Item coins = Inventory.get("Coins");

            if (coins != null){
                coinsBefore = coins.getAmount();
            }
            GrandExchange.collect();
            sleepUntil(()-> Inventory.contains("Coins"), 10000, 1000);
            coins = Inventory.get("Coins");

            int coinsAfter = 0;
            if (coins != null){
                coinsAfter = coins.getAmount();
            }

            utils.increaseItemQuantity(collectedAmount);
            utils.addSpentCoins(-(coinsAfter-coinsBefore));
            GrandExchange.close();
            sleepUntil(() -> !GrandExchange.isOpen(), 1000, 3000);
            utils.setRandomMouseSpeed();
            return Calculations.random(1000, 3000);
        } else if (offerStatus == Status.BUY) {
            utils.nextActiveItem();
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