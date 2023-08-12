package banker;

import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@ScriptManifest(version = 1.0, author = "dreamwiver", category = Category.MISC, name = "DW Bank Sorter")

public class Main extends TaskScript {

    @Override
    public void onStart(){

        if (!Bank.isOpen()){
            Bank.open();
            sleepUntil(() -> Bank.isOpen(), 1000, 200);
        }

        List<Item> bankItems = Bank.all();
        List<Item> nonNullItems = bankItems.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}