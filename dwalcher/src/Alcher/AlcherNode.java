package Alcher;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.grandexchange.GrandExchange;

public class AlcherNode extends Node{

    Utils utils;

    public AlcherNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return (utils.timer.elapsed() >= 1000 * 60 * 10);
    }

    @Override
    public int priority(){
        return 5;
    }

    @Override
    public int execute() {
        log("AQUI Estem");
        return Calculations.random(200,3000);
    }
}
