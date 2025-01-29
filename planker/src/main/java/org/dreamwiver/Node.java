package org.dreamwiver;

import org.dreambot.api.script.TaskNode;

public class Node extends TaskNode {


    public Node(){
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