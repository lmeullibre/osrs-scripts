package Alcher;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.mouse.MouseSettings;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.dreambot.api.methods.input.mouse.MouseSettings.setSpeed;

@ScriptManifest(version = 1.1, author = "dreamwiver", category = Category.MAGIC, name = "DW Alcher Lite")
public class MainClass extends TaskScript {
    private Utils utils = new Utils();
    private GUI gui;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Override
    public void onStart(){
        addNodes(new BuyItemsNode(utils), new AlchItemsNode(utils), new StopNode(utils));
        utils.loadProfitableItems();
        SwingUtilities.invokeLater(() -> gui = new GUI(utils));
        utils.startTiming();
        setSpeed(10);
    }

    public void onPaint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(10, 68, 200, 62); // adjust this to fit your text

        g.setColor(Color.BLACK);
        if (utils.getActiveItem() != null) {
            g.drawString("Active item: " + utils.getActiveItem().getName() ,12,80);
        }
        if (utils.getActiveItemQuantity() != -1) {
            g.drawString("Items left: " + utils.getActiveItemQuantity() ,12,95);
        }
        g.drawString("Coins spent: " + utils.getSpentCoins() ,12,110);
        double alchsPerHour = utils.getAlchsPerHour();
        double expPerHour = alchsPerHour * 65; // 65 exp gained per alch
        g.drawString("Experience per hour: " + (int)expPerHour, 12, 125);
    }
}