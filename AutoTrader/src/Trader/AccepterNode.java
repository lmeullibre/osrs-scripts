package Trader;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.wrappers.widgets.message.Message;

public class AccepterNode extends Node  {

    private boolean incomingTrade = false;
    private String trader;


    @Override
    public int priority(){
        return 2;
    }

    AccepterNode(Utils utils){
        super(utils);
        ScriptManager.getScriptManager().addListener(new ChatListener() {
            @Override
            public void onTradeMessage(Message message) {
                incomingTrade = true;
                trader = message.getUsername();
            }
        });
    }

    @Override
    public boolean accept() {
        return utils.isStarted() && incomingTrade;
    }

    @Override
    public int execute() {
        Trade.tradeWithPlayer(trader);
        sleepUntil(()-> Trade.isOpen(), 5000, 500);
        incomingTrade = false;
        return Calculations.random(200, 1000);
    }
}
