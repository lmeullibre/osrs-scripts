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
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

public class AlchItemsNode extends Node {
    private Utils utils;

    public AlchItemsNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        // Get active item and its quantity
        if (!utils.isRunning()) return false;
        Item activeItem = utils.getActiveItem();
        int quantity = utils.getActiveItemQuantity();

        // Check if the active item is not null, quantity is more than zero and there's no offer on the Grand Exchange for it
        if (activeItem != null && quantity > 0) {
            GrandExchangeItem offer = GrandExchange.getItem(activeItem.getID());
            return offer == null || offer.getStatus() == Status.EMPTY;
        }

        return false;
    }

    @Override
    public int priority() {
        return 1; // This should have the highest priority as it checks for the fundamental requirement
    }

    @Override
    public int execute() {
        // Occasionally rotate the camera
        if (Math.random() < utils.getMistakeRate()) { // use mistakeRate to decide if to rotate camera
            Camera.rotateTo(Calculations.random(0, 360), Calculations.random(0, 90));
        }

        // Check if bank is open
        if (!Bank.isOpen() && !Inventory.contains(utils.getActiveItem().getName())) {
            // Random mis-click when trying to open bank
            if (Math.random() < utils.getMistakeRate()) { // use mistakeRate to decide if to mis-click
                // Open a random tab
                Tab[] tabs = {Tab.COMBAT, Tab.INVENTORY, Tab.EQUIPMENT, Tab.PRAYER, Tab.MAGIC};
                Tab randomTab = tabs[Calculations.random(0, tabs.length - 1)];
                Tabs.open(randomTab);
                return Calculations.random(1000, 2000); // Wait a bit before next action
            }

            // Open bank if it's not open
            Bank.open();
            sleepUntil(() -> Bank.isOpen(), 2000, 1000);
        }

        // Get active item and its quantity
        Item activeItem = utils.getActiveItem();
        int quantity = utils.getActiveItemQuantity();

        if(!Inventory.contains(activeItem.getName())){
            // Withdraw the item from the bank
            Bank.setWithdrawMode(BankMode.NOTE);
            Bank.withdraw(activeItem.getName(), quantity);
            sleepUntil(() -> Inventory.contains(activeItem.getName()), 2000, 1000);
            Bank.close();
            sleepUntil(()-> !Bank.isOpen(), 3000, 3000);
        }

        Item itemToAlch = Inventory.get(activeItem.getName());
        if (itemToAlch != null) {
            if (Magic.canCast(Normal.HIGH_LEVEL_ALCHEMY)) {
                int initialNoteCount = Inventory.count(itemToAlch.getID()); // Get the initial note count

                // Random mis-click when trying to alch
                if (Math.random() < utils.getMistakeRate()) { // use mistakeRate to decide if to mis-click
                    // Open a random tab
                    Tab[] tabs = {Tab.COMBAT, Tab.SKILLS, Tab.EMOTES, Tab.QUEST, Tab.INVENTORY, Tab.EQUIPMENT, Tab.PRAYER, Tab.MAGIC};
                    Tab randomTab = tabs[Calculations.random(0, tabs.length - 1)];
                    Tabs.open(randomTab);
                    return Calculations.random(1000, 2000); // Wait a bit before next action
                }

                if (Math.random() < utils.getMistakeRate()) { // use mistakeRate to decide if to mis-click
                    // Select a random spell
                    Normal[] allSpells = Normal.values();
                    Normal randomSpell = allSpells[Calculations.random(0, allSpells.length - 1)];

                    // Try to cast the random spell
                    Magic.castSpell(randomSpell);
                    return Calculations.random(1000, 2000); // Wait a bit before next action
                }

                Magic.castSpellOn(Normal.HIGH_LEVEL_ALCHEMY, itemToAlch);
                sleepUntil(() -> Inventory.count(itemToAlch.getID()) < initialNoteCount, 2000, 1000);

                int earned = itemToAlch.getHighAlchValue() - LivePrices.get("Nature rune");
                utils.incrementProfit(earned);
                // Subtract one from the amount of items in utils
                int currentAmount = utils.getActiveItemQuantity();
                utils.incrementAlchsPerformed();
                utils.setActiveItemQuantity(currentAmount - 1);
            }
        }
        utils.setRandomMouseSpeed();
        return Calculations.random(1000, 3000);
    }
}