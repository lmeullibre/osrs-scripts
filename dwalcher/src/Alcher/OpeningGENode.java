package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.wrappers.interactive.NPC;

public class OpeningGENode extends Node{
    Utils utils;
    public OpeningGENode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept(){
        return NPCs.closest("Grand Exchange Clerk") != null && NPCs.closest("Grand Exchange Clerk").hasAction("Exchange");
    }

    @Override
    public int priority(){
        return 2;
    }

    @Override
    public int execute() {
        utils.timer.start();
        log("-------");
        log(utils.getJsonContent());
        log("-------");
        if (NPCs.closest("Grand Exchange Clerk") != null){
            NPC geClerk = NPCs.closest("Grand Exchange Clerk");
            geClerk.interact("Exchange");
            sleepUntil(()-> GrandExchange.isGeneralOpen(), 2000, 200);
            if (GrandExchange.openBuyScreen(1)){
                sleepUntil(()-> GrandExchange.isBuyOpen(), 2000, 200);
            } else if (GrandExchange.openBuyScreen(0)){
                sleepUntil(()-> GrandExchange.isBuyOpen(), 2000, 200);
            } else if (GrandExchange.openBuyScreen(2)){
                sleepUntil(()-> GrandExchange.isBuyOpen(), 2000, 200);
            }
            else {
                log("WE ARE FULL CAPACITY");
            }
        }
        return Calculations.random(300, 2000);
    }
}
