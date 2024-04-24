package Alcher;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.grandexchange.Status;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.magic.Spellbook;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AlchItemsNode extends Node {
    private Utils utils;

    public AlchItemsNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return utils.isRunning() && utils.isReady()  && Magic.canCast(Normal.HIGH_LEVEL_ALCHEMY) && utils.getActiveItem() != null && utils.getActiveItemQuantity() > 0;
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public int execute() {
        log("Alching");

        if (Math.random() < utils.getMistakeRate()) {
            Camera.rotateTo(Calculations.random(0, 360), Calculations.random(0, 90));
        }

        if (!Bank.isOpen() && !Inventory.contains(utils.getActiveItem().getName())) {
            if (Math.random() < utils.getMistakeRate()) {
                Tab[] tabs = {Tab.COMBAT, Tab.INVENTORY, Tab.EQUIPMENT, Tab.PRAYER, Tab.MAGIC};
                Tab randomTab = tabs[Calculations.random(0, tabs.length - 1)];
                Tabs.open(randomTab);
                return Calculations.random(1000, 2000);
            }

            Bank.open();
            sleepUntil(() -> Bank.isOpen(), 2000, 1000);
        }

        Item activeItem = utils.getActiveItem();
        int quantity = utils.getActiveItemQuantity();


        if(!Inventory.contains(activeItem.getName())){
            Bank.setWithdrawMode(BankMode.NOTE);
            Bank.withdraw(activeItem.getName(), quantity);
            sleepUntil(() -> Inventory.contains(activeItem.getName()), 2000, 1000);
            Bank.close();
            sleepUntil(()-> !Bank.isOpen(), 3000, 3000);
        }

        Item itemToAlch = Inventory.get(activeItem.getName());
        if (itemToAlch != null) {
            if (Magic.canCast(Normal.HIGH_LEVEL_ALCHEMY)) {
                int initialNoteCount = Inventory.count(itemToAlch.getID());

                if (Math.random() < utils.getMistakeRate()) {
                    Tab[] tabs = {Tab.COMBAT, Tab.SKILLS, Tab.EMOTES, Tab.QUEST, Tab.INVENTORY, Tab.EQUIPMENT, Tab.PRAYER, Tab.MAGIC};
                    Tab randomTab = tabs[Calculations.random(0, tabs.length - 1)];
                    Tabs.open(randomTab);
                    return Calculations.random(1000, 2000);
                }

                if (Math.random() < utils.getMistakeRate()) {
                    Normal[] allSpells = Normal.values();
                    Normal randomSpell = allSpells[Calculations.random(0, allSpells.length - 1)];

                    Magic.castSpell(randomSpell);
                    return Calculations.random(1000, 2000);
                }

                if (Inventory.slotContains(15, itemToAlch.getID())){
                    Inventory.drag(itemToAlch.getID(), 15);
                    sleepUntil(() ->Inventory.slotContains(15, itemToAlch.getID()), 10000, 1000);

                }

                Magic.castSpellOn(Normal.HIGH_LEVEL_ALCHEMY, itemToAlch);
                sleepUntil(() -> Inventory.count(itemToAlch.getID()) < initialNoteCount, 2000, 1000);

                int earned = itemToAlch.getHighAlchValue() - utils.getNatureRunePrice();
                utils.incrementProfit(earned);
                int currentAmount = utils.getActiveItemQuantity();
                utils.incrementAlchsPerformed();
                utils.setActiveItemQuantity(currentAmount - 1);
            }
        }

        if (utils.getActiveItemQuantity() == 0) {
            String activeItemName = (utils.getActiveItem() != null) ? utils.getActiveItem().getName() : "";
            String[] excludedItems = {"Staff of fire", "Nature rune", "Coins", activeItemName};
            Item[] inventoryItems = Inventory.all().toArray(new Item[0]);
            if (inventoryItems != null) {
                for (Item item : inventoryItems) {
                    if (item != null && !Arrays.asList(excludedItems).contains(item.getName()) && Magic.canCast(Normal.HIGH_LEVEL_ALCHEMY)) {
                        int initialNoteCount = Inventory.count(item.getID());
                        Magic.castSpellOn(Normal.HIGH_LEVEL_ALCHEMY, item);
                        sleepUntil(() -> Inventory.count(item.getID()) < initialNoteCount, 2000, 1000);

                        int earned = item.getHighAlchValue() - utils.getNatureRunePrice();
                        utils.incrementProfit(earned);
                        utils.incrementAlchsPerformed();
                    }
                }
            }
        }

        utils.setRandomMouseSpeed();
        return Calculations.random(1000, 3000);
    }
}