package org.dreamwiver;

import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.util.HealthBar;
import org.dreambot.api.wrappers.interactive.util.HealthBarData;

public class Banker extends Node {
    private static final Area BANK_AREA = new Area(3132, 3630, 3135, 3627);

    @Override
    public boolean accept() {
        // Accept if inventory is full
        return Inventory.isFull();
    }

    @Override
    public int priority() {
        // Higher priority than plank picking when inventory is full
        return 2;
    }

    @Override
    public int execute() {
        // Walk to bank if not in area
        if (!BANK_AREA.contains(Players.getLocal())) {
            log("Walking to bank");
            Walking.walk(BANK_AREA.getRandomTile());
            sleepUntil(() -> Players.getLocal().isMoving(), 3000, 1000);
            sleepUntil(() -> !Players.getLocal().isMoving(), 5000, 1000);
        }

        // Open bank and deposit items
        if (!Bank.isOpen()) {
            NPC banker = NPCs.closest("Banker");
            if (banker == null) {
                log("Could not find the banker");
                return Calculations.random(500, 1000);
            }

            Bank.open();
            sleepUntil(Bank::isOpen, 3000, 1000);
        }

        if (Bank.isOpen()) {
            log("Depositing items");
            Bank.depositAllItems();
            Bank.close();
            sleepUntil(() -> !Bank.isOpen(), 3000, 1000);
        }

        if (Walking.getRunEnergy() == 100 && Players.getLocal().getHealthPercent() == 100){
            return Calculations.random(500, 2000);
        }

        GameObject pool = GameObjects.closest("Pool of Refreshment");
        if (pool != null && pool.interact()) {
            log("Using pool of refreshment");
            sleepUntil(() -> Players.getLocal().isMoving(), 3000, 200);
            sleepUntil(() -> Players.getLocal().isStandingStill(), 5000, 1000);
        }
        return Calculations.random(500, 2000);
    }
}
