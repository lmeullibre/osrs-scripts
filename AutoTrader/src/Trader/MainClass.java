package Trader;

import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.trade.TradeUser;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.AnimationListener;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.core.Instance;

import javax.swing.*;
import java.awt.*;

@ScriptManifest(version = 1.0, author = "dreamwiver", category = Category.MISC, name = "Auto Trader")
public class MainClass extends TaskScript implements ChatListener {
    private Utils utils;
    private GUI gui;

    public Utils getUtils() {
        return this.utils;
    }


    @Override
    public void onStart() {
        Tabs.open(Tab.INVENTORY);
        sleepUntil(() -> Tabs.isOpen(Tab.INVENTORY), 1000, 1000);
        utils = new Utils();
        addNodes(new IdleNode(utils), new AccepterNode(utils), new TradingNode(utils), new SummaryNode(utils), new BankNode(utils));
        SwingUtilities.invokeLater(() -> gui = new GUI(utils));
    }

    public void onPaint(Graphics2D g) {
            TaskNode previousNode = getLastTaskNode();
            if (previousNode != null && previousNode.getClass() != null){
                String s = previousNode.getClass().getName();
                if (s == "Trader.AccepterNode") s = "Trade received";
                else if (s == "Trader.TradingNode") s = "Trading, waiting for other user to accept the trade";
                else if (s == "Trader.IdleNode") s = "Waiting for someone to trade";
                else if ( s == "Trader.SummaryNode") s = "Trade summary";
                else if ( s == "Trader.BankNode") s = "Bringing items to bank";
                g.drawString("State: " + s, 12, 300);
                if (Instance.getProxyName() != null){
                    g.drawString("Proxy name: "+Instance.getProxyName(), 12, 320);
                }
                else { g.drawString("No proxy found", 12, 320);
                }
            }
        }
}
