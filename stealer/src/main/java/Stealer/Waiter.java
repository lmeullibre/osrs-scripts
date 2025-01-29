package Stealer;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Camera;

import org.dreambot.api.wrappers.items.Item;

import java.awt.Point;
import java.util.Random;

public class Waiter extends Node {
    private final Random random = new Random();
    private long lastAFKTime = System.currentTimeMillis();
    private boolean isTabSwitching = false;

    public Waiter(Utils utils) {
        super(utils);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean accept() {
        return true;
    }

    @Override
    public int execute() {
        if (shouldSimulateTaskSwitch()) {
            simulateTaskSwitch();
            return Calculations.random(8000, 20000);
        }

        if (random.nextDouble() < 0.1) {
            return Calculations.random(1000, 3000);
        }

        Item moneyPouch = Inventory.get("Coin pouch");
        if (moneyPouch != null) {
            Point currentMousePos = Mouse.getPosition();
            Mouse.move(new Point(
                    currentMousePos.x + Calculations.random(-5, 5),
                    currentMousePos.y + Calculations.random(-5, 5)
            ));

            sleep(Calculations.random(200, 700));
            moneyPouch.interact();
        }

        if (random.nextDouble() < 0.1) {
            Camera.rotateTo(Calculations.random(0, 360), Calculations.random(0, 100));
        }

        if (random.nextDouble() < 0.1) {
            moveMouseToTaskbar();
        }

        return Calculations.random(500, 2000);
    }

    private boolean shouldSimulateTaskSwitch() {
        long currentTime = System.currentTimeMillis();
        if (isTabSwitching) {
            if (currentTime - lastAFKTime > Calculations.random(15000, 30000)) {
                isTabSwitching = false;
            }
            return true;
        }
        if (random.nextDouble() < 0.03) {
            lastAFKTime = currentTime;
            isTabSwitching = true;
            return true;
        }
        return false;
    }

    private void simulateTaskSwitch() {
        moveMouseToTaskbar();
        Mouse.loseFocus(Calculations.random(3000, 10000));
    }

    private void moveMouseToTaskbar() {
        int screenWidth = 800;
        int screenHeight = 600;
        int taskbarY = screenHeight - Calculations.random(5, 30);
        Mouse.move(new Point(Calculations.random(0, screenWidth), taskbarY));
    }
}
