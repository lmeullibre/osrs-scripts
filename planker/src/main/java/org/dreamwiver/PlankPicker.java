package org.dreamwiver;

import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.items.GroundItem;

import java.util.Arrays;
import java.util.List;

public class PlankPicker extends Node {
    private final List<Tile> plankLocations = Arrays.asList(
            new Tile(3148, 3671),
            new Tile(3154, 3670),
            new Tile(3171, 3680),
            new Tile(3182, 3669),
            new Tile(3154, 3659)
    );

    private int currentPlankIndex = 0;
    private static final String PLANK_NAME = "Plank";
    private static final int MAX_INVENTORY_SIZE = 28;

    @Override
    public boolean accept() {
        return !Inventory.isFull();
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int execute() {
        Tile currentTarget = plankLocations.get(currentPlankIndex);
        log("Checking plank location: " + currentPlankIndex);

        if (!Client.getViewport().isOnGameScreen(Map.tileToScreen(currentTarget))) {
            log("Walking to plank location");
            Walking.walk(currentTarget);
            return Calculations.random(500, 1000);
        }

        GroundItem plank = GroundItems.closest(obj ->
                obj.getName().equals(PLANK_NAME) &&
                        obj.getTile().distance(currentTarget) < 2
        );

        if (plank != null && plank.interact("Take")) {
            log("Picking up plank");
            sleepUntil(() -> !plank.isOnScreen(), 10000, 1000);

            // Move to next plank location
            currentPlankIndex = (currentPlankIndex + 1) % plankLocations.size();
            return Calculations.random(300, 500);
        }

        log("No plank found, moving to next location");
        currentPlankIndex = (currentPlankIndex + 1) % plankLocations.size();
        return Calculations.random(200, 400);
    }
}
