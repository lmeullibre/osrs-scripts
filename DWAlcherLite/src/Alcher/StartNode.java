package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;

import org.dreambot.api.script.ScriptManager;


public class StartNode extends Node {
    private Utils utils;

    public StartNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return utils.isRunning() && !utils.isReady();
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public int execute() {
        log("Setting everything up");

        if (!Bank.isOpen()) {
            Bank.open();
            return Calculations.random(1000, 3000);
        }

        if (!Inventory.isEmpty()){
            Bank.depositAllExcept("Nature rune", "Staff of fire");
        }


        boolean hasNatureRunes = Inventory.contains("Nature rune");
        boolean hasFireStaff = Inventory.contains("Staff of fire") || Equipment.contains("Staff of fire");

        if (!hasNatureRunes && Bank.contains("Nature rune")) {
            Bank.withdraw("Nature rune", Bank.count("Nature rune"));  // Withdraw all available Nature Runes
            System.out.println("Nature runes withdrawn.");
        }

        if (!hasFireStaff && Bank.contains("Staff of fire")) {
            Bank.withdraw("Staff of fire", 1);
            sleepUntil(()-> Inventory.contains("Staff of fire"), 2000, 1000);
        }

        if (!Inventory.contains("Nature rune") || !((Inventory.contains("Staff of fire") || Equipment.contains("Staff of fire"))) ) {
            System.err.println("Missing essential items, please check the bank!");
            utils.shouldStop = true;
            ScriptManager.getScriptManager().stop();
        }
        if (!Equipment.contains("Staff of fire") && Inventory.contains(("Staff of fire"))){
            Inventory.interact("Staff of fire", "wield");
            sleepUntil(()-> Equipment.contains("Staff of fire"), 1000, 500);
        }


        utils.setIsReady();

        return Calculations.random(1000, 3000);
    }
}
