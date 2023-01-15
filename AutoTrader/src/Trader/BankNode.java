package Trader;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.NPC;

public class BankNode extends Node {

    public BankNode(Utils utils) {
        super(utils);
    }

    @Override
    public int priority(){
        return 2;
    }

    @Override
    public boolean accept() {
        return utils.isStarted() && !Inventory.isEmpty();
    }

    @Override
    public int execute() {
        NPC banker = NPCs.closest("Banker");
        if (banker != null){
            if (banker.hasAction("Bank")){
                banker.interact("Bank");
                sleepUntil(()-> Players.getLocal().isMoving(), 1000, 200);
                sleepUntil(()-> Bank.isOpen(), 4000, 1000);
                Bank.depositAllItems();
                sleepUntil(()-> Inventory.isEmpty(), 4000, 1000);
                Bank.close();
                sleepUntil(()-> !Bank.isOpen(), 4000, 1000);
                if (utils.getStartingTile().canReach()){
                    Walking.walk(utils.getStartingTile());
                    sleepUntil(()-> Players.getLocal().getTile() == utils.getStartingTile(), 1000, 200);
                }
            }
        }
        return Calculations.random(500, 2000);
    }
}
