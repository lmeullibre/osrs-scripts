package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;

public class GEWalkerNode extends Node {

    Utils utils;

    public GEWalkerNode(Utils utils) {
        super(utils);
    }

    @Override
    public boolean accept() {
        return true;
    }

    @Override
    public int priority(){
        return 1;
    }

    @Override
    public int execute() {
        //Walking.walk(new Area(3153, 3502, 3177, 3478).getRandomTile());
        //sleepUntil(()-> Players.getLocal().isMoving(), 1000, 1000);
        return Calculations.random(200,3000);
    }

}
