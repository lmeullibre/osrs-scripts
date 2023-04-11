package Alcher;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.utilities.Timer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class Utils {
    private boolean isRunning;
    private String jsonContent;
    private int item_id;
    public Timer timer;

    private Area geArea = new Area(3153, 3502, 3177, 3478);
    public HashMap<Integer, Integer> prices;
    ArrayList<LocalItem> localItems;




    public Utils(){
        prices = new HashMap<>();
        localItems = new ArrayList<>();
        isRunning = true;
        timer = new Timer();
        try {
            jsonContent = new String(Files.readAllBytes(new File("F:\\unaltre\\dwalcher\\src\\Alcher\\raw.json").toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void setRunning(){
        this.isRunning = true;
    }
}
