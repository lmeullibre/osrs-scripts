package Slayer;


import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.core.Instance;

import java.awt.*;

@ScriptManifest(version = 1.0, author="tpm", category = Category.SLAYER, name="Giant slayer")
public class MainClass extends TaskScript {

    @Override
    public void onStart() {
        addNodes(new AttackNode(), new LootNode(), new BuryNode(), new EatNode(), new GoToBossNode(), new AttackBossNode(), new DialogEnterBossNode(), new BeforeBossNode());
    }

    public void onPaint(Graphics2D g) {
        TaskNode previousNode = getLastTaskNode();
        g.drawString("State: " + previousNode.getClass().getName() ,12,77);
    }
}


