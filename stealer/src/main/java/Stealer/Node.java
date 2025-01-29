// Node.java
package Stealer;

import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.wrappers.widgets.message.Message;

public abstract class Node extends TaskNode implements ItemContainerListener, ChatListener {
    Utils utils;

    public Node(Utils utils) {
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