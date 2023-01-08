package Trader;

import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.trade.TradeUser;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.core.Instance;

import java.awt.*;

@ScriptManifest(version = 1.0, author = "dreamwiver", category = Category.MISC, name = "Auto Trader")
public class MainClass extends TaskScript implements ChatListener {
    private Utils utils;

    public Utils getUtils() {
        return this.utils;
    }

    @Override
    public void onStart() {
        Utils utils = getUtils();
        addNodes(new IdleNode(), new AccepterNode(), new TradingNode(), new SummaryNode());
    }

    public void onPaint(Graphics2D g) {
        TaskNode previousNode = getLastTaskNode();
        String s = previousNode.getClass().getCanonicalName();
        if (s == "Trader.AccepterNode") s = "Trade received";
        else if (s == "Trader.TradingNode") s = "Trading, waiting for other user to accept the trade";
        else if (s == "Trader.IdleNode") s = "Waiting for someone to trade";
        else if ( s == "Trader.SummaryNode") s = "Trade summary";
        g.drawString("State: " + s, 12, 300);
        if (Instance.getProxyName() != null){
            g.drawString("Proxy name: "+Instance.getProxyName(), 12, 320);
        }
        else { g.drawString("No proxy found", 12, 320);
        }
    }
}
