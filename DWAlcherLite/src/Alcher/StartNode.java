package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.script.ScriptManager;

/**
 * StartNode class handles the initialization and setup of the Alcher script,
 * ensuring that all required items are available before proceeding.
 */
public class StartNode extends Node {
    private Utils utils;

    /**
     * Constructor for the StartNode class.
     * @param utils Utilities object providing shared methods and properties.
     */
    public StartNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    /**
     * Determines if this node should be executed based on the script's state.
     * @return true if the script is running and not yet fully initialized.
     */
    @Override
    public boolean accept() {
        return utils.isRunning() && !utils.isReady();
    }

    /**
     * Provides the priority level for the execution order of this node.
     * @return an integer representing the priority, with 0 as the highest.
     */
    @Override
    public int priority() {
        return 0;
    }

    /**
     * Executes the node's actions to set up the environment by ensuring all necessary items are in place.
     * @return int The time in milliseconds to delay after executing this node.
     */
    @Override
    public int execute() {
        log("Setting everything up");

        if (!Bank.isOpen()) {
            Bank.open();
            return Calculations.random(1000, 3000);
        }

        if (!Inventory.isEmpty()) {
            Bank.depositAllExcept("Nature rune", "Staff of fire");
        }

        boolean hasNatureRunes = Inventory.contains("Nature rune");
        boolean hasFireStaff = Inventory.contains("Staff of fire") || Equipment.contains("Staff of fire");

        if (!hasNatureRunes && Bank.contains("Nature rune")) {
            Bank.withdraw("Nature rune", Bank.count("Nature rune"));
            System.out.println("Withdrawing Nature runes.");
        }

        if (!hasFireStaff && Bank.contains("Staff of fire")) {
            Bank.withdraw("Staff of fire", 1);
            sleepUntil(() -> Inventory.contains("Staff of fire"), 1000, 2000);
        }

        // Check for missing essential items and inform the user
        StringBuilder missingItems = new StringBuilder();
        if (!Inventory.contains("Nature rune") && !Bank.contains("Nature rune")) {
            missingItems.append("Nature rune, ");
        }
        if (!hasFireStaff && !Bank.contains("Staff of fire")) {
            missingItems.append("Staff of fire, ");
        }

        if (missingItems.length() > 0) {
            missingItems.setLength(missingItems.length() - 2); // Remove the trailing comma and space
            System.err.println("Missing essential items: " + missingItems + ". Please check the bank!");
            utils.shouldStop = true;
            ScriptManager.getScriptManager().stop();
        }

        if (!Equipment.contains("Staff of fire") && Inventory.contains("Staff of fire")) {
            Inventory.interact("Staff of fire", "wield");
            sleepUntil(() -> Equipment.contains("Staff of fire"), 1000, 2000);
        }

        utils.setIsReady();
        return Calculations.random(1000, 3000);
    }
}
