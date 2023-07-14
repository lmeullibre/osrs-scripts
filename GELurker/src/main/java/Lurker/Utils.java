package Lurker;

import org.dreambot.api.methods.map.Area;

import java.util.HashSet;
import java.util.Set;

public class Utils {
    private Area grandExchangeArea = new Area(3142, 3513, 3184, 3472);
    private Area safeArea = new Area(3156, 3497, 3172, 3482);
    private Set<String> excludedItems = new HashSet<>();

    private int total = 0;
    private boolean start;
    private int minimum = 1;

    public Utils(){
        start = false;
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

    public void setTotal(int newItemPrice){
        total = total + newItemPrice;
    }


    public void addExcludedItem(String item) {
        excludedItems.add(item);
    }

    public void removeExcludedItem(String item) {
        excludedItems.remove(item.toLowerCase());
    }

    public boolean isItemExcluded(String item) {
        return excludedItems.contains(item.toLowerCase());
    }


    public Area getSafeArea(){
        return safeArea;
    }

}
