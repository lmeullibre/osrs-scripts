package Lurker;

import org.dreambot.api.methods.map.Area;

public class Utils {
    private Area grandExchangeArea =  new Area(3154, 3480, 3174, 3499);
    private Area bigArea = new Area(3141, 3513, 3186, 3470);
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

    public Area getBigArea(){return this.bigArea;}

    public void setTotal(int newItemPrice){
        total = total + newItemPrice;
    }

    public int getTotal() {
        return total;
    }

}
