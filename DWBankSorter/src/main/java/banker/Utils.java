package banker;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class Utils {

    public static Server startWebSocketServer() throws Exception {
        Server server = new Server(8080); // Port 8080 or another port you prefer

        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(BankWebSocketHandler.class);
            }
        };

        server.setHandler(wsHandler);

        server.start();
        return server; // Return the server instance for further handling
    }
}