package banker;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.dreambot.api.utilities.Logger.log;

public class Utils {
    private static final String SOCKET_URL = "http://localhost:5000";
    private static Socket socket;
    public List<Integer> sortedItemIds;

    private String sessionId;

    static {
        // Shutdown hook to close the socket when the application is stopping
        Runtime.getRuntime().addShutdownHook(new Thread(Utils::closeSocket));
    }
    public Utils() {
        sortedItemIds = new ArrayList<>();
        this.sessionId = UUID.randomUUID().toString();
    }


    public void initSocket() {
        try {
            socket = IO.socket(SOCKET_URL);

            socket.on("sorted_items_data", new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                    if (args.length == 0) {
                        log("No data received for sorted_items_data event.");
                        return;
                    }

                    JSONArray itemsArray;

                    if(args[0] instanceof JSONArray) {
                        itemsArray = (JSONArray) args[0];
                    } else {
                        log("Unexpected data type for sorted_items_data event: " + args[0].getClass());
                        return;
                    }

                    List<Integer> itemIds = new ArrayList<>();
                    for (int i = 0; i < itemsArray.length(); i++) {
                        try {
                            int itemId = itemsArray.getInt(i);
                            itemIds.add(itemId);
                        } catch (JSONException e) {
                            log("Error reading integer value at index " + i + " from sorted_items_data: " + e.getMessage());
                        }
                    }

                    sortedItemIds = itemIds; // Set the sortedItemIds field

                    // At this point, 'itemIds' is a List<Integer> containing all the received item IDs
                    log("Received item IDs: " + itemIds);
                }
            });


            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected to server.");
                    sendSessionId(); // Send the session ID right after establishing the connection
                }
            }).on("message_for_bot", new Emitter.Listener() {  // This is an example event.
                @Override
                public void call(Object... args) {
                    if (args.length == 0) {
                        System.out.println("No data received.");
                        return;
                    }

                    if (args[0] instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) args[0];
                        System.out.println("Received JSONArray from server: " + jsonArray.toString());
                    } else if (args[0] instanceof JSONObject) {
                        JSONObject jsonObject = (JSONObject) args[0];
                        System.out.println("Received JSONObject from server: " + jsonObject.toString());
                    } else {
                        System.out.println("Unexpected data type: " + args[0].getClass());
                    }
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Disconnected from server.");
                }
            });

            socket.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendToServer(String message) {
        if (socket != null && socket.connected()) {
            socket.emit("relay_message_to_bot", message);  // Example event. Match this with your server's expected events.
        } else {
            System.out.println("Socket is not connected.");
        }
    }

    public static void closeSocket() {
        if (socket != null) {
            socket.disconnect();
            log("Socket closed on shutdown.");
        }
    }

    private void sendSessionId() {
        if (socket != null && socket.connected()) {
            JSONObject sessionInfo = new JSONObject();
            try {
                sessionInfo.put("sessionId", sessionId);
                socket.emit("session_init", sessionInfo); // Custom event to send session ID
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}