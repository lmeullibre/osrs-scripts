package Lurker;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.NPC;

public class BankNode extends Node {

    public BankNode(Utils utils) {
        super(utils, "Banking");
    }

    @Override
    public int priority(){
        return 2;
    }

    @Override
    public boolean accept() {
        return Inventory.isFull() && (NPCs.closest("Banker") != null);
    }

    @Override
    public int execute() {
        NPC banker = NPCs.closest("Banker");
        if (banker != null){
            if (banker.hasAction("Bank")){
                banker.interact("Bank");
                sleepUntil(()-> Players.getLocal().isMoving(), 1000, 1000);
                sleepUntil(()-> Bank.isOpen(), 1000, 1000);
                Bank.depositAllItems();
                sleepUntil(()-> Inventory.isEmpty(), 1000, 1000);
                Bank.close();
                sleepUntil(()-> !Bank.isOpen(), 1000, 1000);
            }
        }
        return Calculations.random(500, 2000);
    }
}
