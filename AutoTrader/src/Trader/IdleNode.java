package Trader;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.widgets.message.Message;

public class IdleNode extends Node {

    public IdleNode(Utils utils) {
        super(utils);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean accept() {
        return utils.isStarted() && Inventory.isEmpty();
    }

    @Override
    public int execute() {
        if (Math.random() < 1.0 / 15.0 && utils.getMessage() != "") {
            Keyboard.type(utils.getMessage());
            return Calculations.random(1000, 3000);
        }
        return Calculations.random(200, 1000);
    }
}

