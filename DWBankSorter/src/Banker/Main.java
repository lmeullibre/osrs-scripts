package Banker;
import java.util.UUID;

import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankTab;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ScriptManifest(version = 1.0, author = "dreamwiver", category = Category.MISC, name = "DW Bank Sorter")

public class Main extends TaskScript {
    private Utils utils;

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    @Override
    public void onStart(){
        if (!Bank.isOpen()){
            Bank.open();
            sleepUntil(() -> Bank.isOpen(), 1000, 200);
        }

        List<Item> bankItems = Bank.all();
        // Filter out null items
        List<Item> nonNullItems = bankItems.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Call the sendPostRequest method with the nonNullItems list
        UUID uuid = UUID.randomUUID();
        try {
            Utils.updateDatabase(nonNullItems, String.valueOf(uuid));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}