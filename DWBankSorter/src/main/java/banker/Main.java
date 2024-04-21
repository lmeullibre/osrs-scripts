package banker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@ScriptManifest(version = 1.06, author = "dreamwiver", category = Category.MISC, name = "DW Bank Sorter")

public class Main extends TaskScript {
    private Utils utils = new Utils();
    private GUI gui;

    private Map<String, Object> itemToMap(Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", item.getID());
        map.put("name", item.getName()); // Assuming getName() method exists
        map.put("quantity", item.getAmount());
        return map;
    }

    @Override
    public void onExit() {
        gui.frame.dispose();
        log("adeiossss");

    }

    @Override
    public void onStart(){
        AtomicInteger order = new AtomicInteger();

        addNodes(new SorterNode(utils));

        if (!Bank.isOpen()){
            Bank.open();
            sleepUntil(() -> Bank.isOpen(), 1000, 200);
        }

        List<org.dreambot.api.wrappers.items.Item> bankItems = Bank.all();
        List<org.dreambot.api.wrappers.items.Item> nonNullItems = bankItems.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("banker/optimized_file.json");
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Map<Integer, BankItem> iconsMap = new Gson().fromJson(reader, new TypeToken<Map<Integer, BankItem>>() {}.getType());
        if (iconsMap == null) {
            log("Error reading icons from JSON file");
            return;
        }
        List<BankItem> customItems = convertToCustomItems(nonNullItems, iconsMap);


        SwingUtilities.invokeLater(() -> {
                 gui = new GUI(customItems, utils);
        });

    }

    private ImageIcon createIconForItem(org.dreambot.api.wrappers.items.Item bankItem) {
        return new ImageIcon(new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB));
    }

    private List<String> getCategoriesForItem(org.dreambot.api.wrappers.items.Item bankItem) {
        return new ArrayList<>();
    }

    private List<BankItem> convertToCustomItems(List<org.dreambot.api.wrappers.items.Item> bankItems, Map<Integer, BankItem> itemDataMap) {
        List<BankItem> customItems = new ArrayList<>();
        for (org.dreambot.api.wrappers.items.Item bankItem : bankItems) {
            BankItem itemData = itemDataMap.get(bankItem.getID());
            if (itemData != null) {
                customItems.add(new BankItem(bankItem.getID(), bankItem.getName(), itemData.getIcon(), bankItem.getAmount(), itemData.getCategories()));
            } else {
                customItems.add(new BankItem(bankItem.getID(), bankItem.getName(), null, bankItem.getAmount(), new ArrayList<>()));
            }
        }
        return customItems;
    }
}