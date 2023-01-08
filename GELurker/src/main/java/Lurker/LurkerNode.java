package Lurker;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.utilities.InventoryMonitor;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class LurkerNode extends Node {

    public LurkerNode(Utils utils) {
        super(utils, "Looking for items");
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public void onInventoryItemAdded(Item item) {
        utils.setTotal(LivePrices.get(item.getID()));
    }

    @Override
    public boolean accept() {
        return utils.getGrandExchangeArea().contains(Players.getLocal()) && !Inventory.isFull();
    }

    @Override
    public int execute() {
        List<GroundItem> items = GroundItems.all();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i) != null && utils.getGrandExchangeArea().contains(items.get(i))) {
                items.get(i).interact("Take");
                sleepUntil(() -> Players.getLocal().isMoving(), 5000, 500);
                int finalI = i;
                sleepUntil(() -> !items.get(finalI).canReach(), 10000, 500);
                break;
            }
        }
        return Calculations.random(500, 2000);
    }
}
