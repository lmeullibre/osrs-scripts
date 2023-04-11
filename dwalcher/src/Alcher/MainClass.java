package Alcher;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.core.Instance;


import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;


@ScriptManifest(version = 1.0, author = "dreamwiver", category = Category.MAGIC, name = "DW Alcher")
public class MainClass extends TaskScript {
    public void request() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        log("-------------------------------------");
        log(content.toString());
        log("-------------------------------------");
    }

    private Utils utils;

    @Override
    public void onStart(){
        try {
            request();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.utils = new Utils();
        byte[] jsonData = new byte[0];
        try {
            jsonData = Files.readAllBytes(Paths.get("F:\\unaltre\\dwalcher\\src\\Alcher\\raw.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = new String(jsonData);
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            int id = jsonObject.get("id").getAsInt();
            int price = new Item(id, 1).getHighAlchValue() - (LivePrices.get(new Item(id, 1)) + LivePrices.get(new Item(561, 1)));
            if (price < 1 || (LivePrices.get(new Item(id, 1))) >= 2000) {
                jsonArray.remove(i);
                i--;
            } else {
                jsonObject.addProperty("price", price);
                log(new Item(id, 1).getName() + " " + LivePrices.get(new Item(id, 1).getID()) + " " + new Item(id, 1).getHighAlchValue());
                utils.localItems.add(new LocalItem(id, price, 0));
            }
        }
        String modifiedJsonString = gson.toJson(jsonArray);
        try {
            Files.write(Paths.get("F:\\unaltre\\dwalcher\\src\\Alcher\\items.json"), modifiedJsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


        addNodes(new WaitForGUI(utils, this));
        //SwingUtilities.invokeLater(() -> new GUI(utils));
    }

    public void onPaint(Graphics2D g) {
        if (getLastTaskNode() !=null && getLastTaskNode().getClass() != null){
            TaskNode previousNode = getLastTaskNode();
            String s = previousNode.getClass().getName();
            g.drawString("State:: " + s, 12, 77);

            if (Instance.getProxyName() != null){
                g.drawString("Proxy name: "+Instance.getProxyName(), 12, 97);
            }
            else { g.drawString("No proxy found", 12, 97);
            }

        }

    }
}
