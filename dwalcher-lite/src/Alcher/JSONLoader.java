package Alcher;

import com.google.gson.*;
import org.dreambot.api.wrappers.items.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSONLoader {
    private static final String URL = "https://gealcher-production.up.railway.app/?members=false";

    public static void loadProfitableItems(Utils utils) {
        try {
            java.net.URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonArray itemsArray = JsonParser.parseString(response.toString()).getAsJsonArray();

                List<Item> items = new ArrayList<>();
                for (int i = 0; i < itemsArray.size(); i++) {
                    JsonObject itemObj = itemsArray.get(i).getAsJsonObject();
                    JsonElement idElement = itemObj.get("id");
                    if (idElement != null && !idElement.isJsonNull()) {
                        int itemId = idElement.getAsInt();
                        Item item = new Item(itemId, 1);
                        items.add(item);
                    } else {
                        System.out.println("Warning: found item with no ID or null ID in the JSON");
                    }
                }

                if (!items.isEmpty()) {
                    utils.setActiveItemQuantity(0);
                }

                for (Item item : items) {
                    utils.addProfitableItem(item);
                }
            } else {
                System.out.println("Failed to fetch data from the microservice. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}