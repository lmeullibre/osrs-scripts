package Stealer;


import org.dreambot.api.methods.Calculations;

import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.util.concurrent.atomic.AtomicBoolean;

public class Stealer extends Node {
    private final AtomicBoolean shouldSteal = new AtomicBoolean(false);

    public Stealer(Utils utils) {
        super(utils);
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean accept() {
        return shouldSteal.get();
    }

    @Override
    public void onGameMessage(Message message) {
        if (message.getMessage().contains("urchin")) {
            shouldSteal.set(true);
        }
    }

    @Override
    public int execute() {
        NPC leo = NPCs.closest("Leo");
        if (leo == null)  return Calculations.random(500, 2000);

        sleepUntil(leo::isInteractedWith, 10000, 1000);
        NPC interactedWith = null;
        try {
            interactedWith = (NPC) leo.getCharacterInteractingWithMe();
        }
        catch(Error e){
            log(e);
        }
        if (interactedWith == null || !interactedWith.getName().equals("Wealthy citizen")) {
            log("Wrong NPC or User interacted with Leo");
            return Calculations.random(500, 2000);
        }

        log(interactedWith.getName());

        // Add human-like delay before pickpocketing
        sleep(Calculations.random(600, 1200));

        interactedWith.interact("Pickpocket");

        // Add small variable delay after initiating pickpocket
        sleep(Calculations.random(200, 400));

        NPC finalInteractedWith = interactedWith;
        sleepUntil(()-> !finalInteractedWith.isInteracting(leo), 20000, 1000);

        shouldSteal.set(false);
        return Calculations.random(500, 2000);
    }
}