package Lurker;

import org.dreambot.api.methods.map.Area;

public class Utils {
    private Area grandExchangeArea = new Area(3150, 3505, 3179, 3476);
    private int total = 0;
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
