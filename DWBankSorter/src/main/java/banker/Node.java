package banker;

import org.dreambot.api.script.TaskNode;

public class Node extends TaskNode {

    private Utils utils;

    public Node(Utils utils){
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return false;
    }

    @Override
    public int execute() {
        return 0;
    }
}