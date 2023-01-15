package Trader;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.wrappers.items.Item;

public abstract class Node extends TaskNode  {

    Utils utils;

    public Node(Utils utils){
        this.utils = utils;
    }
}
