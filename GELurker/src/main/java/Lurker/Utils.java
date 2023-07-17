package Lurker;

import org.dreambot.api.methods.map.Area;

public class Utils {
    private Area grandExchangeArea = new Area(3150, 3505, 3179, 3476);
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

    public int getTotal() {
        return total;
    }

}
