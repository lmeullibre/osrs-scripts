package Lurker;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.wrappers.items.Item;

public abstract class Node extends TaskNode implements ItemContainerListener {

    Utils utils;

    String status;

    public Node(Utils utils, String status){
        this.utils = utils;
        this.status = status;
    }

    @Override
    public boolean accept() {
        return false;
    }

    @Override
    public int execute() {
        return 0;
    }
}
