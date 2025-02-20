package Stealer;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;

import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.core.Instance;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

@ScriptManifest(version = 1.00, author = "dreamwiver", category = Category.THIEVING, name = "Wealthy Stealer")
public class Main extends TaskScript {

    private Utils utils = new Utils();
    private Instant startTime;
    private long startXp;
    private long startCoins;
    private long currentCoins;
    private long initialCoins;
    private long initialXp;

    @Override
    public void onStart() {
        startTime = Instant.now();
        initialXp = Skills.getExperience(Skill.THIEVING);
        initialCoins = countCoins();

        startXp = 0;
        startCoins = 0;
        currentCoins = 0;

        Stealer stealer = new Stealer(utils);
        Waiter waiter = new Waiter(utils);
        Walker walker = new Walker(utils);
        Banker banker = new Banker(utils);

        Instance.getInstance().addEventListener(stealer);
        Instance.getInstance().addEventListener(waiter);
        addNodes(waiter, stealer, walker, banker);

    }

    private long countCoins() {
        Item coins = Inventory.get("Coins");
        return coins != null ? coins.getAmount() : 0;
    }

    private String formatStateForDisplay(String stateName) {
        switch (stateName) {
            case "Waiter":
                return "Waiting";
            case "Stealer":
                return "Stealing";
            default:
                return stateName;
        }
    }

    private String formatTime(Duration duration) {
        long hours = duration.toHours();
        long minutes = (duration.toMinutes() % 60);
        long seconds = (duration.getSeconds() % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String formatNumber(long number) {
        if (number < 1000) return String.valueOf(number);
        if (number < 1000000) return String.format("%.1fK", number / 1000.0);
        return String.format("%.1fM", number / 1000000.0);
    }

    public void onPaint(Graphics2D g) {
        TaskNode previousNode = getLastTaskNode();
        if (previousNode == null) return;
        String stateName = previousNode.getClass().getSimpleName();
        String displayState = formatStateForDisplay(stateName);

        long currentXp = Skills.getExperience(Skill.THIEVING) - initialXp;
        currentCoins = countCoins() - initialCoins;
        Duration runTime = Duration.between(startTime, Instant.now());
        double hoursElapsed = runTime.toMillis() / 3600000.0;

        long xpGained = currentXp - startXp;
        long coinsGained = currentCoins - startCoins;

        int xpPerHour = hoursElapsed > 0 ? (int) (xpGained / hoursElapsed) : 0;
        int coinsPerHour = hoursElapsed > 0 ? (int) (coinsGained / hoursElapsed) : 0;

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.WHITE);

        int y = 77;
        int lineHeight = 15;

        g.drawString("State: " + displayState, 12, y);
        y += lineHeight;
        g.drawString("Time: " + formatTime(runTime), 12, y);
        y += lineHeight;
        g.drawString("XP Gained: " + formatNumber(xpGained), 12, y);
        y += lineHeight;
        g.drawString("XP/hr: " + formatNumber(xpPerHour), 12, y);
        y += lineHeight;
        g.drawString("Coins Gained: " + formatNumber(coinsGained), 12, y);
        y += lineHeight;
        g.drawString("Coins/hr: " + formatNumber(coinsPerHour), 12, y);
    }
}