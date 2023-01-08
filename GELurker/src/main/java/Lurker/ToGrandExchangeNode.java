package Lurker;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.TaskNode;

public class ToGrandExchangeNode extends Node {

    public ToGrandExchangeNode(Utils utils) {
        super(utils, "Going to grand exchange");
    }


    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean accept() {
        return !utils.getGrandExchangeArea().contains(Players.getLocal().getTile());
    }

    @Override
    public int execute() {
        Walking.walk(utils.getGrandExchangeArea().getRandomTile());
        sleepUntil(()-> Players.getLocal().isMoving(), 1000, 1000);
        return Calculations.random(3000, 6000);
    }
}
