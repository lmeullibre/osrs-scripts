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

@ScriptManifest(version = 1.05, author = "dreamwiver", category = Category.MAGIC, name = "DW Alcher Lite")
public class MainClass extends TaskScript {
    private Utils utils = new Utils();
    private GUI gui;

    @Override
    public void onStart(){
        addNodes(new BuyItemsNode(utils), new AlchItemsNode(utils) ,new StartNode(utils));
        SwingUtilities.invokeLater(() -> gui = new GUI(utils));
        utils.startTiming();
        setSpeed(10);
    }

    @Override
    public void onExit() {
        gui.frame.dispose();
    }

    public void onPaint(Graphics2D g) {
        int screenHeight = 400;  // Adjust based on actual screen size
        int yStart = screenHeight - 50;

        Font titleFont = new Font("SansSerif", Font.BOLD, 12);
        Font defaultFont = new Font("SansSerif", Font.PLAIN, 10);

        g.setColor(new Color(0, 0, 0, 200));  // Semi-transparent background
        g.fillRect(10, yStart, 300, 120);

        g.setColor(Color.YELLOW);
        g.drawRect(10, yStart, 300, 120);

        g.setFont(titleFont);
        g.setColor(Color.YELLOW);
        g.drawString("Bot Status Overview", 14, yStart + 12);

        g.setFont(defaultFont);
        g.setColor(Color.WHITE);

        Item activeItem = utils.getActiveItem();
        if (activeItem != null) {
            g.drawString("Active item: " + activeItem.getName(), 14, yStart + 27);
            if (utils.getActiveItemQuantity() != -1) {
                g.drawString("Items left: " + utils.getActiveItemQuantity(), 14, yStart + 42);
            }
        } else {
            g.drawString("No active item currently.", 14, yStart + 27);
        }

        g.drawString("Coins spent: " + utils.getSpentCoins(), 14, yStart + 57);

        double alchsPerHour = utils.getAlchsPerHour();
        double expPerHour = alchsPerHour * 65;  // Assuming each alch gives 65 XP
        g.drawString("Experience per hour: " + (int) expPerHour, 14, yStart + 72);

        if (utils.isKilled()) {
            g.setColor(Color.RED);
            g.drawString("Bot has been killed - check logs!", 14, yStart + 87);
        }

        int profit = utils.getProfit();
        g.setColor(Color.GREEN);
        g.drawString("Profit: " + utils.getProfit() + " coins", 14, yStart + 102);
    }
}