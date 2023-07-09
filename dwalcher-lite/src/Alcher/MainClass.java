package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@ScriptManifest(version = 1.0, author = "dreamwiver", category = Category.MAGIC, name = "DW Alcher Lite")
public class MainClass extends TaskScript {
    private Utils utils = new Utils();
    private GUI gui;


    @Override
    public void onStart(){
        addNodes(new BuyItemsNode(utils), new AlchItemsNode(utils), new StopNode(utils));
        utils.loadProfitableItems();
        SwingUtilities.invokeLater(() -> gui = new GUI(utils));
    }

    public void onPaint(Graphics2D g) {
        if (utils.getActiveItem() != null) g.drawString("Active item: " + utils.getActiveItem().getName() ,12,80);
        if (utils.getActiveItemQuantity() != -1) g.drawString("Amount bought: " + utils.getActiveItemQuantity() ,12,90);
        g.drawString("Coins spent: " + utils.getSpentCoins() ,12,100);
        TaskNode previousNode = getLastTaskNode();
        if (previousNode != null) g.drawString("State: " + previousNode.getClass().getName() ,12,110);
    }
}