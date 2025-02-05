package Stealer;

import org.dreambot.api.methods.map.Area;

public class Utils {

    private final boolean start;

    private final Area area = new Area(1673, 3114, 1687, 3099);

    public Utils(){
        start = false;
    }


    public Area getArea() {
        return this.area;
    }
}