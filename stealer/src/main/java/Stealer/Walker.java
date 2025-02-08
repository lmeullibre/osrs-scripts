package Stealer;


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
    public Walker(Utils utils) {
        super(utils);
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean accept() {
        Tile playerPosition = Players.getLocal().getTile();
        Area bazaarArea = utils.getArea();
        return !bazaarArea.contains(playerPosition);
    }

    @Override
    public int execute() {
        Walking.walk(new Tile(1680, 3108));
        return Calculations.random(500, 2000);
    }
}
