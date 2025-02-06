package Lurker;

import com.google.gson.Gson;
import org.dreambot.api.methods.map.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {


    private Area grandExchangeArea =  new Area(3154, 3480, 3174, 3499);
    private Area bigArea = new Area(3141, 3513, 3186, 3470);

    private String statusURL;

    private int total = 0;
    private boolean start;

    private List<String> nonWantedItems;

    private int minimum = 1;
    public Utils(){
        start = false;
        nonWantedItems = new ArrayList<>();
    }

    public int getMinimum(){
        return this.minimum;
    }

    public boolean isStarted(){return start;}

    public void start(){
        this.start = true;
    }

    public void setMinimum(int newMinimum){
        this.minimum = newMinimum;
    }

    public Area getGrandExchangeArea(){
        return grandExchangeArea;
    }

    public Area getBigArea(){return this.bigArea;}

    public void setTotal(int newItemPrice){
        total = total + newItemPrice;
    }

    public int getTotal() {
        return total;
    }

    public void addNonWantedItem(String itemName){
        nonWantedItems.add(itemName.toLowerCase());
    }

    public boolean isNonWantedItem(String itemName){
        return nonWantedItems.contains(itemName.toLowerCase());
    }

    public void removeNonWantedItem(String item) {
        nonWantedItems.remove(item);
    }

}
