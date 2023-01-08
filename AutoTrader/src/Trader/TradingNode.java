package Trader;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.trade.TradeUser;
import org.dreambot.api.script.TaskNode;

public class TradingNode extends TaskNode {

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean accept() {
        return Trade.isOpen(1);
    }

    @Override
    public int execute() {
        if (Trade.isOpen(1)) {
            if (Trade.hasAcceptedTrade(TradeUser.THEM)){
                Trade.acceptTrade(1);
                sleepUntil(()-> Trade.isOpen(2), 2000, 300 );
            }
        }
        return Calculations.random(200, 3000);
    }
}
