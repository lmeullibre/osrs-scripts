package banker;

import org.dreambot.api.wrappers.items.Item;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.IOException;
import java.util.List;

@WebSocket
public class BankWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        Main.registerSession(session); // Notify the Main class about the new connection
    }

    public static void sendItems(Session session, List<Item> items) {
        try {
            // Assuming you have a method to convert the items to a JSON string
            String jsonItems = convertToJson(items);
            session.getRemote().sendString(jsonItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertToJson(List<Item> items) {
        // Logic to convert the items list to a JSON string
        // You might want to use a library like Gson or Jackson here
        return null;
    }
}