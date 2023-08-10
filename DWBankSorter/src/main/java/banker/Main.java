package banker;

import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;
import org.eclipse.jetty.websocket.api.Session;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main extends TaskScript {
    private static Session session; // WebSocket session

    @Override
    public void onStart(){
        try {
            Utils.startWebSocketServer(); // Start the WebSocket server
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Existing code to open bank and get items
        if (!Bank.isOpen()){
            Bank.open();
            sleepUntil(() -> Bank.isOpen(), 1000, 200);
        }

        List<Item> bankItems = Bank.all();
        List<Item> nonNullItems = bankItems.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (session != null) {
            BankWebSocketHandler.sendItems(session, nonNullItems);
        }
    }

    public static void registerSession(Session newSession) {
        session = newSession; // Store the session for later use
    }
}