package org.dreamwiver;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;


@ScriptManifest(version = 1.00, author = "dreamwiver", category = Category.MONEYMAKING, name = "Graveyard Plank Picker")
public class Main extends TaskScript {

    @Override
    public void onStart() {
        log("Starting script");
        Area startArea = new Area(3124, 3639, 3143, 3618);
        Tile startingTile = Players.getLocal().getTile();
        if (!startArea.contains(startingTile)){
            log("Cannot start script. Please move your character to the Ferox Enclave");
            stop();
        }
        addNodes(new Banker(), new PlankPicker());
    }
}