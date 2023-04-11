package Alcher;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.wrappers.items.Item;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.*;

public class BuyerNode extends Node{

    Utils utils;

    public BuyerNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return GrandExchange.isBuyOpen();
    }

    @Override
    public int priority(){
        return 4;
    }

    @Override
    public int execute() {
        LocalItem maxPriceItem = Collections.max(utils.localItems, Comparator.comparingInt(o -> o.price));
        GrandExchange.searchItem(new Item(maxPriceItem.id, 1).getName());
        WidgetChild objecte = GrandExchange.getItemChildInSearch(new Item(maxPriceItem.id, 1).getName());
        if (objecte != null && objecte.hasAction("Select")) {
            objecte.interact("Select");
            sleepUntil(()-> GrandExchange.itemVisible(objecte), 3000, 200);
            log("arribem aqui i no dona error suposo");
            GrandExchange.setPrice(LivePrices.getHigh(new Item(maxPriceItem.id, 1).getName()));
        }
        //log("TIMEOUT TIMEOUT TIMEOUT"+utils.timer.elapsed());
        return Calculations.random(200,3000);
    }
}
