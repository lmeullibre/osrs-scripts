package Lurker;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.core.Instance;

import java.awt.*;

@ScriptManifest(version = 1.0, author = "dreamwiver", category = Category.MISC, name = "Grand Exchange Lurker")
public class MainClass extends TaskScript {

    private Utils utils;

    public Utils getUtils() {
        return this.utils;
    }

    @Override
    public void onStart() {
        Tabs.open(Tab.INVENTORY);
        sleepUntil(() -> Tabs.isOpen(Tab.INVENTORY), 1000, 1000);
        utils = new Utils();
        addNodes(new ToGrandExchangeNode(getUtils()), new LurkerNode(getUtils()), new BankNode(getUtils()));
    }

    public void onPaint(Graphics2D g) {
        TaskNode previousNode = getLastTaskNode();
        String s = previousNode.getClass().getName();
        if (s == "Lurker.LurkerNode") s = "Looking for items";
        else if (s == "Lurker.BankNode") s = "Banking items";
        else if (s == "Lurker.ToGrandExchangeNode") s = "Going to Grand Exchange";
        g.drawString("State: " + s, 12, 77);
        //g.drawString("Total collected: " + utils.getTotal() + " coins.", 12, 97);
        if (Instance.getProxyName() != null){
            g.drawString("Proxy name: "+Instance.getProxyName(), 12, 97);
        }
        else { g.drawString("No proxy found", 12, 97);
        }
    }
}
