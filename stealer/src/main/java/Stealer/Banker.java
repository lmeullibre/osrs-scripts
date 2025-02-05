package Stealer;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;

import java.util.concurrent.atomic.AtomicBoolean;

public class Banker extends Node {
    public Banker(Utils utils) {
        super(utils);
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean accept() {
        return Inventory.getEmptySlots() <= 4;
    }

    @Override
    public int execute() {
        Bank.open();
        if (Bank.isOpen()) {
            Bank.depositAllItems();
            Bank.close();
        }
        return Calculations.random(500, 2000);
    }
}