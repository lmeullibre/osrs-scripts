package Trader;

import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private int total = 0;
    private String message;
    private List<String> messages = new ArrayList<>();

    private boolean start;
    private Tile startingTile;

    public Utils(){
        this.total = 0;
        this.message = "";
        this.start = false;
        this.startingTile = Players.getLocal().getTile();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public String getRandomMessage() {
        if (!messages.isEmpty()) {
            int index = (int) (Math.random() * messages.size());
            return messages.get(index);
        }
        return "";  // default message if the list is empty
    }

    public void start(){
        this.start = true;
    }
    public boolean isStarted(){
        return start;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public boolean isBankNear(){
        return true;
    }

    public Tile getStartingTile(){
        return startingTile;
    }

    public String getMessage(){
        return this.message;
    }
}
