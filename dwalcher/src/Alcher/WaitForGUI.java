package Alcher;

import org.dreambot.api.script.TaskNode;

public class WaitForGUI extends TaskNode {

    Utils utils;
    MainClass main;

    public WaitForGUI(Utils utils, MainClass main){
        this.utils = utils;
        this.main = main;
    }

    @Override
    public boolean accept(){
        return utils.isRunning();
    }

    @Override
    public int execute() {
        main.addNodes(new GEWalkerNode(utils), new OpeningGENode(utils), new BuyerNode(utils), new AlcherNode(utils));
        main.removeNodes(this);
        return 0;
    }
}
