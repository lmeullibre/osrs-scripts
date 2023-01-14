package Lurker;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.wrappers.items.Item;

public abstract class Node extends TaskNode implements ItemContainerListener {

    Utils utils;


    public Node(Utils utils){
        this.utils = utils;
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
