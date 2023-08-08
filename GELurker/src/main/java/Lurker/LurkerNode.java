package Lurker;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.utilities.InventoryMonitor;
import org.dreambot.api.wrappers.items.GroundItem;
import java.util.Comparator;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class LurkerNode extends Node {

    public LurkerNode(Utils utils) {
        super(utils);
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
        return utils.isStarted() && utils.fetchStatusFromServer() && utils.getBigArea().contains(Players.getLocal()) && !Inventory.isFull();
    }

    public Tile getClosestTileInArea(Area area, Tile playerTile) {
        Tile closestTile = null;
        double closestDistance = Double.MAX_VALUE;
        for (Tile tile : area.getTiles()) {
            double distance = playerTile.distance(tile);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestTile = tile;
            }
        }
        return closestTile;
    }

    @Override
    public int execute() {
        if (!Walking.isRunEnabled()){
            Walking.toggleRun();
        };
        List<GroundItem> items = GroundItems.all();
        items.sort(Comparator.comparingInt(item -> (int) item.distance(Players.getLocal())));
        for (GroundItem item : items) {
            if (Inventory.isFull()) {
                break;
            }
            if (item != null && utils.getGrandExchangeArea().contains(item)) {
                if (LivePrices.get(item.getItem()) >= utils.getMinimum() && !utils.isNonWantedItem(item.getName())) {
                    item.interact("Take");
                    sleepUntil(() -> Players.getLocal().isMoving(), 1000, 200);
                    sleepUntil(() -> !item.exists(), 2000, 500);
                    if (!utils.fetchStatusFromServer()) return Calculations.random(500, 2000); // check status after sleepUntil
                    if (utils.getBigArea().contains(Players.getLocal().getTile())) {
                        List<GroundItem> itemsOutside = GroundItems.all();
                        itemsOutside.sort(Comparator.comparingInt(i -> (int) i.distance(Players.getLocal())));
                        for (GroundItem itemOutside : itemsOutside) {
                            if (Inventory.isFull()) {
                                break;
                            }
                            if (itemOutside != null && utils.getBigArea().contains(itemOutside)) {
                                if (LivePrices.get(itemOutside.getItem()) >= utils.getMinimum() && !utils.isNonWantedItem(itemOutside.getName())){
                                    itemOutside.interact("Take");
                                    sleepUntil(() -> !itemOutside.exists(), 2000, 500);
                                    if (!utils.fetchStatusFromServer()) return Calculations.random(500, 2000);
                                }
                            }
                        }
                        Tile closestTile = getClosestTileInArea(utils.getGrandExchangeArea(), Players.getLocal().getTile());
                        if (closestTile != null) {
                            Walking.walk(closestTile);
                            if (!utils.fetchStatusFromServer()) return Calculations.random(500, 2000); // check status after walking
                        }
                    }
                }
            }
        }
        return Calculations.random(500, 2000);
    }
}
