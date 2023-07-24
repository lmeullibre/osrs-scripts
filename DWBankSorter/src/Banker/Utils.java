package Banker;

import org.dreambot.api.wrappers.items.Item;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

public class Utils {

    private static final String URL = "http://127.0.0.1:5000/api/items";

    public static void sendPostRequest(List<Item> items) {
        if (items == null) {
            System.out.println("The items list is null");
            return;
        }

        StringBuilder jsonInputString = new StringBuilder("[");

        for (Item item : items) {
            if (item == null) {
                System.out.println("An item in the list is null");
                continue;
            }
            jsonInputString.append("{\"id\":").append(item.getID()).append(", \"name\":\"").append(item.getName()).append("\", \"quantity\":").append(item.getAmount()).append("},");
        }

        jsonInputString = new StringBuilder(jsonInputString.substring(0, jsonInputString.length() - 1) + "]");

        try {
            URL urlObj = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println(code);

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}