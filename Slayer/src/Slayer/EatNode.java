package Slayer;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.items.Item;

import java.util.ArrayList;
import java.util.List;

public class EatNode extends TaskNode {

    @Override
    public int priority(){
        return 6;
    }

    @Override
    public boolean accept() {
        return Players.getLocal().getHealthPercent() < 50;
    }

    @Override
    public int execute() {
        List<Item> list=new ArrayList<Item>();
        list = Inventory.all();
        for (int i = 0; i < list.size(); ++i){
            if (list.get(i) != null && list.get(i).hasAction("Eat")){
                list.get(i).interact(
                    "Eat");
                return 300;
            }
        }
        return 300;
    }
}
