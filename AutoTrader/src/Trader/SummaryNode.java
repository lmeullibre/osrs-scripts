package Trader;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.trade.TradeUser;
import org.dreambot.api.script.TaskNode;

public class SummaryNode extends TaskNode {

    @Override
    public int priority(){
        return 4;
    }

    @Override
    public boolean accept() {
        return Trade.isOpen(2);
    }

    @Override
    public int execute() {
        if (Trade.isOpen(2)){
            if (Trade.hasAcceptedTrade(TradeUser.THEM)){
                Trade.acceptTrade(2);
            }
        }
        return Calculations.random(300, 2000);
    }
}
