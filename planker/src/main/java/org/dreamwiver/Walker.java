package org.dreamwiver;


import org.dreambot.api.methods.Calculations;

import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.util.concurrent.atomic.AtomicBoolean;

public class Walker extends Node {

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean accept() {
        Tile playerPosition = Players.getLocal().getTile();
        Area startArea = new Area(3124, 3639, 3143, 3618);
        return !startArea.contains(playerPosition);
    }

    @Override
    public int execute() {
        Walking.walk(new Area(3124, 3639, 3143, 3618).getRandomTile());
        return Calculations.random(500, 2000);
    }
}