package Trader;

import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.NPC;

public class Utils {
    private int total = 0;
    private String message;
    private boolean start;
    private Tile startingTile;

    public Utils(){
        this.total = 0;
        this.message = "";
        this.start = false;
        this.startingTile = Players.getLocal().getTile();
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
