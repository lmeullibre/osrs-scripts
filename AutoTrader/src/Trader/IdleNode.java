package Trader;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

public class IdleNode extends TaskNode {

    @Override
    public int priority(){
        return 1;
    }

    @Override
    public boolean accept() {
        return true;
    }

    @Override
    public int execute() {
        return Calculations.random(500, 3000);
    }
}
